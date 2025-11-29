<template>
  <el-main>
    <!-- 搜索框 -->
    <el-form
      :model="parm"
      ref="searchRef"
      label-width="80px"
      :inline="true"
      size="small"
    >
      <el-form-item label="姓名">
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
      <el-table-column label="缴费金额" prop="payWaterMoney"></el-table-column>
      <el-table-column label="所属月份" prop="payWaterMonth"></el-table-column>
      <el-table-column label="表显" prop="waterNum"></el-table-column>
      <el-table-column label="缴费状态" prop="payWaterStatus">
        <template slot-scope="scope">
          <el-tag
            type="success"
            size="small"
            v-if="scope.row.payWaterStatus == '0'"
            >未缴费</el-tag
          >
          <el-tag
            type="danger"
            size="small"
            v-if="scope.row.payWaterStatus == '1'"
            >已缴费</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="280">
        <template slot-scope="scope">
          <el-button
            type="primary"
            size="small"
            icon="el-icon-edit"
            @click="editBtn(scope.row)"
            >编辑</el-button
          >
          <el-button
            type="danger"
            size="small"
            icon="el-icon-delete"
            @click="deleteBtn(scope.row)"
            >删除</el-button
          >
          <el-button
            type="warning"
            size="small"
            @click="payFeeWaterBtn(scope.row)"
            v-if="scope.row.payWaterStatus == '0'"
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
          ref="addFrom"
          :rules="rules"
          label-width="80px"
          :inline="true"
          size="small"
        >
          <el-form-item label="栋数" prop="buildId">
            <el-select v-model="addModel.buildId" @change="selectBuild">
              <el-option
                v-for="item in buildList"
                :key="item.buildId"
                :label="item.name"
                :value="item.buildId"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="单元" prop="unitId">
            <el-select v-model="addModel.unitId" @change="selectUnit">
              <el-option
                v-for="item in unitList"
                :key="item.unitId"
                :label="item.unitName"
                :value="item.unitId"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="房屋编号" prop="houseId">
            <el-select v-model="addModel.houseId">
              <el-option
                v-for="item in houseList"
                :key="item.houseId"
                :label="item.houseNum"
                :value="item.houseId"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="所属月份" prop="payWaterMonth" size="small">
            <el-date-picker
              format="yyyy-MM"
              value-format="yyyy-MM"
              v-model="addModel.payWaterMonth"
              type="month"
              placeholder="选择月份"
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item label="缴费金额" prop="payWaterMoney">
            <el-input v-model="addModel.payWaterMoney"></el-input>
          </el-form-item>
          <el-form-item label="表显" style="margin-left: 15px" prop="waterNum">
            <el-input v-model="addModel.waterNum"></el-input>
          </el-form-item>
          <el-form-item label="缴费状态" prop="payWaterStatus">
            <el-radio-group v-model="addModel.payWaterStatus">
              <el-radio :label="'0'">未缴费</el-radio>
              <el-radio :label="'1'">已缴费</el-radio>
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
  addFeeWaterApi,
  editFeeWaterApi,
  deleteFeeWaterApi,
  payFeeWaterApi,
} from "@/api/feeWater";
import {
  getBuildListApi,
  getUnitListByBuildIdApi,
  getHouseByUnitIdApi,
} from "@/api/feePower";
import SysDialog from "@/components/system/SysDialog.vue";
export default {
  components: { SysDialog },
  data() {
    return {
      //房屋列表
      houseList: [],
      //单元列表
      unitList: [],
      //栋数列表
      buildList: [],
      //校验规则
      rules: {
        buildId: [
          {
            trigger: "change",
            required: true,
            message: "请选择栋数",
          },
        ],
        unitId: [
          {
            trigger: "change",
            required: true,
            message: "请选择单元",
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
            message: "填写所属月份",
          },
        ],
        payWaterMoney: [
          {
            trigger: "change",
            required: true,
            message: "请填写缴费金额",
          },
        ],
        waterNum: [
          {
            trigger: "change",
            required: true,
            message: "请填写表显",
          },
        ],
        payWaterStatus: [
          {
            trigger: "change",
            required: true,
            message: "请选择缴费状态",
          },
        ],
      },
      //新增或编辑弹窗数据
      addModel: {
        editType: "", //编辑状态 0：新增，1：编辑
        waterId: "", //主键
        houseId: "", //房屋id
        userId: "", //用户id
        payWaterMonth: "", //缴费年月
        payWaterMoney: "", //缴费金额
        waterNum: "", //表显
        payWaterStatus: "", //缴费状态 0：未缴费，1：已缴费
        payWaterTime: "", //缴费时间
        buildId: "", //栋数id
        name: "", //栋数名称
        unitId: "", //单元id
        unitName: "", //单元名称
        houseNum: "", //房屋编号
      },
      //弹窗属性
      addDialog: {
        title: "",
        width: 620,
        height: 260,
        visible: false,
      },
      tableHeight: 0, //表格高度
      tableList: [], //表格数据
      //搜素框参数
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
    //缴费
    async payFeeWaterBtn(row){
      const confirm = await this.$myconfirm('确定要缴费吗?');
      if(confirm){
        let parm = {
          waterId:row.waterId,
          payWaterStatus:'1',
        };
        let res = await payFeeWaterApi(parm);
        if(res && res.code == 200){
          //提示信息，刷新页面
          this.getList();
          this.$message.success(res.msg);
        }
      }
    },
    //选择单元事件
    async selectUnit(val) {
      //清空原来的数据
      this.addModel.houseId = "";
      this.getHouseByUnitId(val);
    },
    //选择栋数事件
    async selectBuild(val) {
      //清空原来的数据
      this.addModel.unitId = "";
      this.addModel.houseId = "";
      //根据栋数id查询对应的单元
      this.getUnitListByBuildId(val)
    },
    //弹窗确认事件
    onConfirm() {
      this.$refs.addFrom.validate(async (valid) => {
        if (valid) {
          let res = null;
          if (this.addModel.editType == "0") {
            //新增
            res = await addFeeWaterApi(this.addModel);
          } else {
            //编辑
            res = await editFeeWaterApi(this.addModel);
          }
          if (res && res.code == 200) {
            //刷新页面，提示信息，关闭弹窗
            this.getList();
            this.$message.success(res.msg);
            this.addDialog.visible = false;
          }
        }
      });
    },
    //弹窗关闭事件
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
      //console.log(confirm);
      if (confirm) {
        let res = await deleteFeeWaterApi({ waterId: row.waterId });
        if (res && res.code == 200) {
          //刷新列表，提示信息
          this.getList();
          this.$message.success(res.msg);
        }
      }
    },
    //编辑按钮
     editBtn(row) {
      this.getUnitListByBuildId(row.buildId);
      this.getHouseByUnitId(row.unitId);
      //清空表单数据
      this.$resetForm("addFrom", this.addModel);
      //设置弹窗属性
      this.addDialog.title = "新增水费";
      this.addDialog.visible = true;
      //设置编辑状态
      this.addModel.editType = "1";
      //把当前要编辑的数据设置到数据域
      this.$objCoppy(row, this.addModel);
    },
    //添加按钮
    addBtn() {
      //清空表单数据
      this.$resetForm("addFrom", this.addModel);
      //设置弹窗属性
      this.addDialog.title = "新增水费";
      this.addDialog.visible = true;
      //设置编辑状态
      this.addModel.editType = "0";
    },
    //重置按钮
    resetBtn() {
      //清空表单
      this.parm.userName = "";
      this.parm.houseNum = "";
      this.getList();
    },
    //查询按钮
    searchBtn() {
      this.getList();
    },
    //查询列表
    async getList() {
      let res = await getListApi(this.parm);
      console.log(res);
      if (res && res.code == 200) {
        this.tableList = res.data.records;
        this.parm.total = res.data.total;
      }
    },
    //查询栋数列表
    async getBuildList() {
      let res = await getBuildListApi();
      //console.log(res);
      if (res && res.code == 200) {
        this.buildList = res.data;
      }
    },
    //根据栋数id查询单元列表
    async getUnitListByBuildId(buildId) {
      let res = await getUnitListByBuildIdApi({ buildId: buildId });
      if (res && res.code == 200) {
        this.unitList = res.data;
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