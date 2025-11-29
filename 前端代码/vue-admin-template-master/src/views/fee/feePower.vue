<template>
  <el-main>
    <!-- 搜索框 -->
    <el-form :model="parm" label-width="80px" :inline="true" size="small">
      <el-form-item label="业主姓名">
        <el-input v-model="parm.userName"></el-input>
      </el-form-item>
      <el-form-item label="房屋编号">
        <el-input v-model="parm.houseNum"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button icon="el-icon-search" @click="searchBtn">查询</el-button>
        <el-button icon="el-icon-close" style="color: #ff7076" @click="resetBtn"
          >重置</el-button
        >
        <el-button icon="el-icon-plus" type="primary" @click="addBtn"
          >新增</el-button
        >
      </el-form-item>
    </el-form>
    <!-- 表格 -->
    <el-table :data="tableList" border stripe :height="tableHeight" size="small">
      <el-table-column label="业主姓名" prop="loginName"></el-table-column>
      <el-table-column label="电话" prop="phone"></el-table-column>
      <el-table-column label="栋数" prop="name"></el-table-column>
      <el-table-column label="单元" prop="unitName"></el-table-column>
      <el-table-column label="房屋编号" prop="houseNum"></el-table-column>
      <el-table-column label="缴费金额" prop="payPowerMoney"></el-table-column>
      <el-table-column label="所属月份" prop="payPowerMonth"></el-table-column>
      <el-table-column label="表显" prop="powerNum"></el-table-column>
      <el-table-column label="缴费状态" prop="payPowerStatus">
        <template slot-scope="scope">
          <el-tag
            type="success"
            size="small"
            v-if="scope.row.payPowerStatus == '0'"
            >未缴费</el-tag
          >
          <el-tag
            type="warning"
            size="small"
            v-if="scope.row.payPowerStatus == '1'"
            >已缴费</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="280">
        <template slot-scope="scope">
          <el-button
            icon="el-icon-edit"
            type="primary"
            size="small"
            @click="editBtn(scope.row)"
            >编辑</el-button
          >
          <el-button
            icon="el-icon-delete"
            type="danger"
            size="small"
            @click="deleteBtn(scope.row)"
            >删除</el-button
          >
           <el-button
            type="warning"
            size="small"
            @click="payFeePowerBtn(scope.row)"
            v-if="scope.row.payPowerStatus == '0'"
            >缴费</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <el-pagination
      @size-change="sizeChange"
      @current-change="currentChange"
      :current-page.sync="parm.currentPage"
      :page-sizes="[10, 20, 40, 80, 100]"
      :page-size="parm.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="parm.total"
      background
    >
    </el-pagination>
    <!-- 弹框 -->
    <sys-dialog
      :title="addDialog.title"
      :width="addDialog.width"
      :height="addDialog.height"
      :visible="addDialog.visible"
      @onClose="onClose"
      @onConfirm="onConfirm"
    >
      <template slot="content">
        <el-form
          :model="addModel"
          ref="addForm"
          :rules="rules"
          label-width="80px"
          :inline="true"
          size="small"
        >
          <el-form-item label="栋数名称" prop="buildId">
            <el-select
              v-model="addModel.buildId"
              placeholder="请选择"
              @change="selectBuild"
            >
              <el-option
                v-for="item in buildNameOptions"
                :key="item.buildId"
                :label="item.name"
                :value="item.buildId"
                :disabled="item.disabled"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="单元名称" prop="unitId" size="small">
            <el-select
              v-model="addModel.unitId"
              placeholder="请选择"
              @change="selectUnit"
            >
              <el-option
                v-for="item in unitNameOptions"
                :key="item.unitId"
                :label="item.unitName"
                :value="item.unitId"
                :disabled="item.disabled"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="房屋编号" prop="houseId" size="small">
            <el-select v-model="addModel.houseId" placeholder="请选择">
              <el-option
                v-for="item in houseList"
                :key="item.houseId"
                :label="item.houseNum"
                :value="item.houseId"
                :disabled="item.disabled"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="所属月份" prop="payPowerMonth" size="small">
            <el-date-picker
              format="yyyy-MM"
              value-format="yyyy-MM"
              v-model="addModel.payPowerMonth"
              type="month"
              placeholder="选择月份"
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item label="缴费金额" prop="payPowerMoney" size="small">
            <el-input v-model="addModel.payPowerMoney"></el-input>
          </el-form-item>
          <el-form-item
            label="表显"
            style="margin-left: 15px"
            prop="powerNum"
            size="small"
          >
            <el-input v-model="addModel.powerNum"></el-input>
          </el-form-item>
          <el-form-item label="缴费状态" prop="payPowerStatus">
            <el-radio-group v-model="addModel.payPowerStatus">
              <el-radio :label="'0'">未缴费</el-radio>
              <el-radio :label="'1'">已交费</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-form>
      </template>
    </sys-dialog>
  </el-main>
</template>
<script>
import {
  getListApi,
  addFeePowerApi,
  editFeePowerApi,
  deleteFeePowerApi,
  getBuildListApi,
  getUnitListByBuildIdApi,
  getHouseByUnitIdApi,
  payFeePowerApi,
} from "@/api/feePower";
import SysDialog from "@/components/system/SysDialog.vue";
import myconfirm from "@/utils/myconfirm";
export default {
  components: { SysDialog },
  data() {
    return {
      houseList: [], //存放房屋列表
      unitNameOptions: [], //单元名称下拉框
      buildNameOptions: [], //栋数名称下拉框
      //校验规则
      rules: {
        buildId: [
          {
            trigger: "change",
            required: true,
            message: "请选择栋数名称",
          },
        ],
        unitId: [
          {
            trigger: "change",
            required: true,
            message: "请选择单元名称",
          },
        ],
        houseId: [
          {
            trigger: "change",
            required: true,
            message: "请选择房屋编号",
          },
        ],
        payPowerMonth: [
          {
            trigger: "change",
            required: true,
            message: "请填写所属月份",
          },
        ],
        payPowerMoney: [
          {
            trigger: "change",
            required: true,
            message: "请填写缴费金额",
          },
        ],
        powerNum: [
          {
            trigger: "change",
            required: true,
            message: "请填写表显",
          },
        ],
        payPowerStatus: [
          {
            trigger: "change",
            required: true,
            message: "请选择缴费状态",
          },
        ],
      },
      //新增或编辑表单数据
      addModel: {
        editType: "", // 0:新增，1:编辑
        name: "", //栋数名称
        unitName: "", //单元名称
        houseNum: "", //房屋编号
        payPowerMonth: "", //所属月份
        payPowerMoney: "", //缴费金额
        powerNum: "", //表显
        payPowerStatus: "", //缴费状态
        buildId: "", //栋数id
        unitId: "", //单元id
        powerId: "", //电费表id
        houseId: "", //房屋id
        userId: "", //业主id
      },
      //弹窗属性
      addDialog: {
        title: "",
        width: 620,
        height: 260,
        visible: false,
      },
      tableHeight: 0,
      //表格数据
      tableList: [],
      //查询参数
      parm: {
        userName: "", //业主姓名
        houseNum: "", //房屋编号
        currentPage: 1, //当前页
        pageSize: 10, //页容量
        total: 0, //总条数
      },
    };
  },
  mounted() {
    this.$nextTick(() => {
      this.tableHeight = window.innerHeight - 178;
    });
  },
  created() {
    this.getList();
    this.getBuildList();
  },
  methods: {
    //缴费按钮
    async payFeePowerBtn(row){
      //提示信息
      const confirm = await this.$myconfirm('确定要缴费吗？');
      if(confirm){
      let parm ={
          powerId:row.powerId,
          payPowerStatus:'1',
        };
      let res= await payFeePowerApi(parm);
      if(res && res.code == 200){
          //刷新列表，提示信息
            this.getList();
            this.$message.success(res.msg);
      }
    }
      
    },
    //弹窗选择单元触发事件
    selectUnit(val) {
      //console.log(val);
      //清空原来的数据
      this.addModel.houseId = "";
      this.getHouseByUnitId(val);
    },
    //弹窗选择栋数触发事件
    async selectBuild(val) {
      //清空原来的数据
      this.addModel.unitId = "";
      this.addModel.houseId = "";
      //跟据栋数id查询对应的单元
      this.getUnitListByBuildId(val);
    },
    //弹窗确认
    onConfirm() {
      this.$refs.addForm.validate(async (valid) => {
        if (valid) {
          let res = null;
          if (this.addModel.editType == "0") {
            // 新增
            res = await addFeePowerApi(this.addModel);
          } else {
            //编辑
            res = await editFeePowerApi(this.addModel);
          }
          if (res && res.code == 200) {
            //刷新列表，提示信息，关闭弹窗
            this.getList();
            this.$message.success(res.msg);
            this.addDialog.visible = false;
          }
        }
      });
    },
    //弹窗关闭
    onClose() {
      this.addDialog.visible = false;
    },
    //页数改变时触发
    currentChange(val) {
      this.parm.currentPage = val;
      this.getList();
    },
    //页容量改变时触发
    sizeChange(val) {
      this.parm.pageSize = val;
      this.getList();
    },
    //删除按钮
    async deleteBtn(row) {
      let confirm = await this.$myconfirm("确定要删除吗？");
      console.log(confirm);
      if (confirm) {
        let res = await deleteFeePowerApi({ powerId: row.powerId });
        if (res && res.code == 200) {
          //刷新列表，提示信息
          this.getList();
          this.$message.success(res.msg);
        }
      }
    },
    //编辑按钮
    editBtn(row) {
      //console.log(row);
      //根据栋数id获取单元列表
      this.getUnitListByBuildId(row.buildId);
      //查询房屋列表
      this.getHouseByUnitId(row.unitId);
      //清除表单数据
      this.$resetForm("addForm", this.addModel);
      //设置编辑属性
      this.addModel.editType = "1"; //0:新增，1:编辑
      //设置弹窗属性
      this.addDialog.title = "编辑电费";
      this.addDialog.visible = true;
      //把当前要编辑的数据设置到数据域
      this.$objCoppy(row, this.addModel);
    },
    //新增按钮
    async addBtn() {
      //清除表单数据
      this.$resetForm("addForm", this.addModel);
      //设置编辑属性
      this.addModel.editType = "0"; //0:新增，1:编辑
      //设置弹窗属性
      this.addDialog.title = "新增电费";
      this.addDialog.visible = true;
    },
    //搜索按钮
    searchBtn() {
      this.getList();
    },
    //重置按钮
    resetBtn() {
      this.parm.userName = "";
      this.parm.houseNum = "";
      this.getList();
    },
    //查询表格列表
    async getList() {
      let res = await getListApi(this.parm);
      if (res && res.code == 200) {
        console.log(res);
        this.tableList = res.data.records;
        this.parm.total = res.data.total;
      }
    },
    //获取栋数列表
    async getBuildList() {
      let res = await getBuildListApi();
      if (res && res.code == 200) {
        //console.log(res);
        this.buildNameOptions = res.data;
      }
    },
    //根据栋数id获取单元列表
    async getUnitListByBuildId(buildId) {
      let res = await getUnitListByBuildIdApi({ buildId: buildId });
      if (res && res.code == 200) {
        //console.log(res);
        this.unitNameOptions = res.data;
      }
    },
    //根据单元id获取房屋列表
    async getHouseByUnitId(unitId) {
      let res = await getHouseByUnitIdApi({ unitId: unitId });
      if (res && res.code == 200) {
        this.houseList = res.data;
      }
    },
  },
};
</script>

<style lang="scss" scoped>
</style>