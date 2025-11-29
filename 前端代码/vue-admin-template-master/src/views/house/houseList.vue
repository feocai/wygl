<template>
  <el-main>
    <!-- 搜索框 -->
    <el-form
      :model="param"
      ref="searchForm"
      label-width="80px"
      :inline="true"
      size="small"
    >
      <el-form-item label="栋数名称">
        <el-input v-model="param.buildName" placeholder="请输入栋数名称"></el-input>
      </el-form-item>
      <el-form-item label="单元名称">
        <el-input v-model="param.unitName" placeholder="请输入单元名称"></el-input>
      </el-form-item>
     <!--  <el-form-item label="房屋编号">
        <el-input v-model="param.houseNum" placeholder="请输入房屋编号"></el-input>
      </el-form-item> -->
      <el-form-item label="使用状态">
        <el-select v-model="param.status">
            <el-option v-for="item in statusOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
            </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button icon="el-icon-search" @click="searchBtn">查询</el-button>
        <el-button
          icon="el-icon-close"
          style="color: #ff7670"
          @click="resetBtn"
          >重置</el-button
        >
        <el-button icon="el-icon-plus" type="primary" @click="addBtn"
          >新增</el-button
        >
      </el-form-item>
    </el-form>
    <!-- 表格 -->
    <el-table :data="tableList" border stripe :height="tableHeight" size='small'>
      <el-table-column prop="name" label="栋数名称"></el-table-column>
       <el-table-column prop="type" label="栋数类型">
        <template slot-scope="scope">
          <el-tag type="success" size="small" v-if="scope.row.type == '0'">普通房</el-tag>
          <el-tag type="danger" size="small" v-if="scope.row.type == '1'">电梯房</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="unitName" label="单元名称"></el-table-column>
      <el-table-column prop="houseNum" label="房屋编号"></el-table-column>
      <el-table-column prop="houseArea" label="使用面积"></el-table-column>
      <el-table-column prop="status" label="使用状态">
        <template slot-scope="scope">
          <el-tag type="success" size="small" v-if="scope.row.status == '0'">未使用</el-tag>
          <el-tag type="danger" size="small"  v-if="scope.row.status == '1'">已使用</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="180" align="center">
        <template slot-scope="scope">
            <el-button type="primary" size="small" @click="editBtn(scope.row)" icon="el-icon-edit">编辑</el-button>
            <el-button type="danger" size="small" @click="deleteBtn(scope.row)" icon="el-icon-delete">删除</el-button>
        </template>
      </el-table-column>

    </el-table>
    <!-- 分页 -->
    <el-pagination
      @size-change="sizeChange"
      @current-change="currentChange"
      :current-page.sync="param.currentPage"
      :page-sizes="[10, 20, 40, 80, 100]"
      :page-size="param.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="param.total" background>
    </el-pagination>
    <!-- 新增或编辑弹窗 -->
    <SysDialog
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
          <el-form-item label="所属栋数" prop="buildValue">
            <el-select v-model="addModel.buildValue" @change="selectBuild">
              <el-option v-for="item in buildList"
                :key="item.buildId"
                :label="item.name"
                :value="item.buildId">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="所属单元" prop="unitId">
           <el-select v-model="addModel.unitId">
            <el-option v-for="item in unitList"
              :key="item.unitId"
              :label="item.unitName"
              :value="item.unitId">
            </el-option>
           </el-select>
          </el-form-item>
          <el-form-item label="房屋编号" prop="houseNum">
            <el-input v-model="addModel.houseNum" placeholder="请输入房屋编号" size="small"></el-input>
          </el-form-item>
          <el-form-item label="房屋面积" style="margin-left:15px;" prop="houseArea">
            <el-input v-model="addModel.houseArea" placeholder="请输入房屋面积" size="small"></el-input>
          </el-form-item>
          <el-form-item label="使用状态" prop="status">
              <el-radio-group v-model="addModel.status">
                <el-radio :label="'0'">未使用</el-radio>
                <el-radio :label="'1'">已使用</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-form>
      </template>
    </SysDialog>
  </el-main>
</template>
<script>
import {
  getListApi,
  addHouseListApi,
  editHouseListApi,
  deleteHouseListApi,
  getUnitListByBuildIdApi,
  getBuildListApi,
} from "@/api/houseList";
import SysDialog from '@/components/system/SysDialog.vue';
export default {
    components:{SysDialog},
  data() {
    return {
      //单元列表
      unitList:[],
      //栋数列表
      buildList:[],
      //使用状态
      statusOptions: [
        {
            value: "0",
            label: "未使用",
            },
            {
            value: "1",
            label: "已使用",
            },],
        //房屋类型
         typeOptions: [
            {
            value: "0",
            label: "普通房",
            },
            {
            value: "1",
            label: "电梯房",
            },],
        //校验规则
        rules:{
            buildValue:[{
                trigger:'change',
                required:true,
                message:'请选择所属栋数'
            }],
            unitId:[{
                trigger:'change',
                required:true,
                message:'请选择所属单元'
            }],
            houseNum:[{
                trigger:'change',
                required:true,
                message:'请填写房屋编号'
            }],
            houseArea:[{
                trigger:'change',
                required:true,
                message:'请填写房屋面积'
            }],
            status:[{
                trigger:'change',
                required:true,
                message:'请选择使用状态'
            }],
        },

      //新增或编辑弹窗参数
      addModel: {
        editType: "", //0：新增，1：编辑
        houseId:'', //房屋id
        unitId:'', //单元id
        houseNum: "", //房屋编号
        houseArea:'',//房屋面积
        status: "", //使用状态 0：未使用， 1：已使用
        buildValue:'',
        unitName:"",
      },
      //定义弹窗属性
      addDialog: {
        title: "",
        width: 610,
        height: 200,
        visible: false,
      },
      //表格高度
      tableHeight: 0,
      //表格数据
      tableList: [],
      //查询参数
      param: {
        houseId: "", //房屋id
        buildName: "", //栋数名称
        unitName: "", //单元名称
        houseNum: "", //房屋编号
        currentPage: 1, //当前第几页
        pageSize: 10, //页容量
        status: "", //使用状态 0：未使用， 1：已使用
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
    //弹窗选择栋数触发事件
    async selectBuild(val){
      //清空原来的数据
      this.addModel.unitId ='';
      //跟据栋数id查询对应的单元
     this.getUnitListByBuildId(val);
    },
    //删除按钮
    async deleteBtn(row){
        let confirm = await this.$myconfirm('确定要删除吗?');
        if(confirm){
            let res = await deleteHouseListApi({houseId:row.houseId});
            if(res && res.code == 200){
                //提示信息，刷新表格
                this.getList();
                this.$message.success(res.msg);
            }
        }
    },
    //编辑按钮
    editBtn(row){
      //根据栋数id查询单元列表
      this.getUnitListByBuildId(row.buildId);
      //清空表单
      this.$resetForm("addForm", this.addModel);
      //设置弹框属性
      this.addDialog.title = "编辑房屋";
      this.addDialog.visible = true;

      //把当前编辑的行数据设置到表单数据域
      this.$objCoppy(row, this.addModel);
      //设置编辑标志
      this.addModel.editType = "1";
      //console.log(row);
      //console.log(this.addModel);
      //栋数回显
      this.addModel.buildValue = row.name;
      //单元回显
      this.addModel.unitId = row.unitName;
    },
     //新增按钮
    addBtn() {
      //清空表单
      this.$resetForm("addForm", this.addModel);
      //设置编辑标识
      this.addModel.editType = "0";
      //设置弹框属性
      this.addDialog.title = "新增房屋";
      this.addDialog.visible = true;
    },
    //弹窗确认事件
    onConfirm() {
      this.$refs.addForm.validate(async (valid) =>{
        if(valid){
          let res = null;
          if(this.addModel.editType == '0'){ //新增
            res = await addHouseListApi(this.addModel);
            if(res && res.code == 200){
              //刷新页面，提示信息，关闭弹窗
              this.getList();
              this.$message.success(res.msg);
              this.addDialog.visible = false;
            }
          }else{//编辑
              res = await editHouseListApi(this.addModel);
              //刷新页面，提示信息，关闭弹窗
              this.getList();
              this.$message.success(res.msg);
              this.addDialog.visible = false;
          }
        }
      })
    },
    //弹窗关闭
    onClose() {
      this.addDialog.visible = false;
    },
    //页数改变时触发
    currentChange(val) {
      this.param.currentPage = val;
      this.getList();
    },
    //页容量改变时触发
    sizeChange(val) {
      this.param.pageSize = val;
      this.getList();
    },
    //重置按钮
    resetBtn() {
      this.param.buildName = "";
      this.param.unitName = "";
      this.param.houseNum = "";
      this.param.status = "";
      this.param.currentPage = 1;
      this.getList();
    },
    //查询按钮
    searchBtn() {
      this.getList();
    },
    //查询列表
    async getList() {
      let res = await getListApi(this.param);
      if (res && res.code == 200) {
        console.log(res);
        this.tableList = res.data.records;
        this.param.total = res.data.total;
      }
    },
    //获取栋数列表
    async getBuildList(){
      let res = await getBuildListApi();
      //console.log(res);
      if(res && res.code == 200){
        //赋值给栋数列表
        this.buildList = res.data;
      }
    },
    //根据栋数id查询单元列表
    async getUnitListByBuildId(buildId){
      let res = await getUnitListByBuildIdApi({buildId:buildId});
      if(res && res.code == 200){
        this.unitList = res.data;
      }
    }
  },
};
</script>

<style lang="scss" scoped>
</style>