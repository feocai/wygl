<template>
  <el-main>
    <!-- 搜索框 
        el-form:就把他当做正常的div一样使用
        :model 表单绑定的数据
        ref：相当于div的id，在当前页面是唯一的
        :inline 为false时，排列在一行
        -->
    <el-form
      :model="parm"
      ref="searchForm"
      label-width="80px"
      :inline="true"
      size="small"
    >
      <el-form-item label="姓名">
        <el-input v-model="parm.loginName"></el-input>
      </el-form-item>
      <el-form-item label="电话">
        <el-input v-model="parm.phone"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="searchBtn" icon="el-icon-search">查询</el-button>
        <el-button @click="resetBtn" icon="el-icon-close" style="color:#FF7670;">重置</el-button>
        <el-button @click="addUser" icon="el-icon-plus" type="primary" v-if="hasPerm('sys:user:add')">新增</el-button
        >
      </el-form-item>
    </el-form>
    <!-- 员工列表 
        :data 表格的数据
        column的prop需要根返回的数据属名对应
        -->
    <el-table
      :data="tableList"
      :height="tableHeight"
      empty-text="暂无信息"
      border
      stripe
      size="small"
    >
      <el-table-column prop="loginName" label="姓名"></el-table-column>
      <el-table-column prop="phone" label="电话"></el-table-column>
      <el-table-column prop="idCard" label="省份证号码"></el-table-column>
      <el-table-column prop="sex" label="性别" align="center">
        <template slot-scope="scope">
          <el-tag size="normal" v-if="scope.row.sex == '1'">男</el-tag>
          <el-tag type="success" size="normal" v-if="scope.row.sex == '0'"
            >女</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column align="center" prop="status" label="是否离职">
        <template slot-scope="scope">
          <el-switch
            :active-value="'0'"
            active-text="否"
            inactive-text="是"
            :inactive-value="'1'"
            v-model="scope.row.status"
            @change="changeStatus(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="isUsed" label="是否启用">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.isUsed"
            :active-value="'0'"
            :inactive-value="'1'"
            active-text="是"
            inactive-text="否"
            @change="changeUsed(scope.row)"
          >
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="290">
        <template slot-scope="scope">
            <el-button type="primary" size="small" @click="editUser(scope.row)" icon="el-icon-edit"  v-if="hasPerm('sys:user:edit')">编辑</el-button>
            <el-button type="primary" size="small" @click="assignRole(scope.row)" icon="el-icon-edit">分配角色</el-button>
            <el-button type="danger" size="small" @click="deleteUser(scope.row)" icon="el-icon-delete" v-if="hasPerm('sys:user:delete')">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 
        size-change 页容量改变时触发事件
        current-change 页数改变时触发事件
        :current-page.sync 当前页数
        :page-size 每页多少条数据
        :total 总条数
        -->
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
      :pager-count="7">
    </el-pagination>
    <!-- 新增或编辑弹窗 -->
    <sys-dialog
      :title="dialog.title"
      :visible="dialog.visible"
      :height="dialog.height"
      :width="dialog.width"
      @onClose="onClose"
      @onConfirm="onConfirm"
    >
      <div slot="content">
        <!-- 新增表单
            :model 表单绑定的数据
            ref 相当于div的一个id，在当前页面唯一
            :rules表单的验证规则
             -->
        <el-form
          :model="addModel"
          ref="addForm"
          :rules="rules"
          label-width="80px"
          :inline="true"
          size="small"
        >
          <el-form-item prop="loginName" label="姓名:">
            <el-input v-model="addModel.loginName"></el-input>
          </el-form-item>
          <el-form-item prop="sex" style="width: 250px" label="性别:">
            <el-radio-group v-model="addModel.sex">
              <el-radio :label="'1'">男</el-radio>
              <el-radio :label="'0'">女</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item prop="phone" label="电话:">
            <el-input v-model="addModel.phone"></el-input>
          </el-form-item>
          <el-form-item prop="idCard" label="身份证:">
            <el-input v-model="addModel.idCard"></el-input>
          </el-form-item>
          <el-form-item label="登录名:">
            <el-input v-model="addModel.username"></el-input>
          </el-form-item>
          <el-form-item label="密码:">
            <el-input type="password" v-model="addModel.password"></el-input>
          </el-form-item>
          <el-form-item prop="status" label="离职:" style="width: 250px">
            <el-radio-group v-model="addModel.status">
              <el-radio :label="'1'">是</el-radio>
              <el-radio :label="'0'">否</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item prop="isUsed" label="启用:" style="width: 250px">
            <el-radio-group v-model="addModel.isUsed">
              <el-radio :label="'1'">否</el-radio>
              <el-radio :label="'0'">是</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-form>
      </div>
    </sys-dialog>
    <!-- 分配角色弹窗 -->
    <sys-dialog
    :title="roleDialog.title"
    :height="roleDialog.height"
    :width="roleDialog.width"
    :visible="roleDialog.visible"
    @onClose="roleClose"
    @onConfirm="roleConfirm"
    >
    <template slot="content">
      <el-table :data="roleList" border stripe :height="roleHeight">
        <el-table-column label="选择" width="50" align="center">
          <template slot-scope="scope">
            <el-radio v-model="radio" :label="scope.row.roleId" @change="getCurrentRow(scope.row)">
              {{""}}
            </el-radio>
          </template>
        </el-table-column>
        <el-table-column prop="roleName" label="角色名称"></el-table-column>
        <el-table-column prop="remark" label="备注"></el-table-column>
      </el-table>
      <el-pagination
        @size-change="roleSizeChange"
        @current-change="roleCurrentChange"
        :current-page.sync="roleParm.currentPage"
        :page-sizes="[10,20, 40, 80, 100]"
        :page-size="roleParm.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="roleParm.total" background>
        :pager-count="7">
      </el-pagination>
    </template>
    </sys-dialog>
  </el-main>
</template>
<script>
import { getUserListApi, addUserApi ,editUserApi,deleteUserApi,getRoleByUserIdApi,assignSaveApi} from "@/api/user";
import SysDialog from "@/components/system/SysDialog";
import{getRoleListApi} from "@/api/role"
export default {
  //组件在使用之前需要注册
  components: {
    SysDialog
  },
  //所有需要在页面上展示的数据，都要在data里面
  data() {
    return {
      assignParm:{
        roleId:'',
        userId:'',
      },

      //角色单选按钮绑定属性
      radio:'',
      //角色表格高度
      roleHeight:0,
      //存储角色列表的数据
      roleList:[],
      //角色列表分页的参数
      roleParm:{
        pageSize:10,
        currentPage:1,
        total:0,
      },
      //定义分配角色弹窗弹窗属性
      roleDialog:{
        title:'',
        height: 400,
        width:800,
        visible:false,
      },
      //表单验证的规则
      rules: {
        loginName: [
          {
            required: true,
            trigger: "change",
            message: "请填写姓名",
          },
        ],
        sex: [
          {
            required: true,
            trigger: "change",
            message: "请选择性别",
          },
        ],
        phone: [
          {
            required: true,
            trigger: "change",
            message: "请填写电话",
          },
        ],
        idCard: [
          {
            required: true,
            trigger: "change",
            message: "请填写身份证",
          },
        ],
        status: [
          {
            required: true,
            trigger: "change",
            message: "请选择是否离职",
          },
        ],
        isUsed: [
          {
            required: true,
            trigger: "change",
            message: "请选择启用状态",
          },
        ],
      },
      //表单绑定的数据
      addModel: {
        userId: "",
        type: "", //0：新增，1：编辑
        username: "",
        sex: "",
        phone: "",
        idCard: "",
        loginName: "",
        password: "",
        status: "",
        isUsed: "",
      },
      dialog: {
        title: "",
        visible: false,
        height: 250,
        width: 610,
      },

      //表格的高度
      tableHeight: 0,
      //搜索框的绑定数据
      parm: {
        loginName: "",
        phone: "",
        currentPage: 1,
        pageSize: 10,
        total: 0,
      },
      //表格数据
      tableList: [],
    };
  },
  created() {
    this.getUserList();
  },

  mounted() {
    this.$nextTick(() => {
      this.tableHeight = window.innerHeight - 178;
    });
  },
  methods: {
   //角色选中事件
   getCurrentRow(row){
    this.assignParm.roleId = row.roleId;
    },
    //角色列表页数改变触发
    roleCurrentChange(val){
      this.roleParm.currentPage = val;
       //获取角色列表
       this.getRoleList();
      
    },
    //角色列表页容量改变时触发
    roleSizeChange(val){
      this.roleParm.pageSize = val;
        //获取角色列表
        this.getRoleList();
    },
    //角色弹窗确认
    async roleConfirm(){
       //console.log(this.assignParm);
       let res = await assignSaveApi(this.assignParm);
       if(res && res.code == 200){
          this.roleDialog.visible =false;
          this.$message.success(res.msg);
       }
    },
    //角色弹窗关闭
    roleClose(){
      this.roleDialog.visible =false;
    },
    //分配角色按钮
    async assignRole(row){
      //默认清空单选框里的数据
      this.radio ='';
      this.assignParm.userId = row.userId;
      //设置弹框属性
      this.roleDialog.title = '为【'+row.loginName+'】分配角色';
      this.roleDialog.visible =true;
      //获取角色列表的数据
      this.getRoleList();
      this.$nextTick(() => {
        this.roleHeight = window.innerHeight - 350;
    });

    //获取用户原来的角色
    let roleRes = await getRoleByUserIdApi({userId:row.userId});
    if(roleRes && roleRes.code == 200){
      //console.log(res);
      if(roleRes.data){
         this.radio = roleRes.data.roleId;
         this.assignParm.roleId = roleRes.data.roleId;
      }
    }
    },
    //获取角色列表
    async getRoleList(){
      let res= await getRoleListApi(this.roleParm);
          if(res && res.code ==200){
            //console.log(res);
            this.roleList = res.data.records;
            this.roleParm.total = res.data.total;
          }
    },
    //重置按钮
    resetBtn(){
        this.parm.phone = '';
        this.parm.loginName = '';
        this.getUserList();
    },
    //搜索按钮
    searchBtn(){
        this.getUserList();
    },
    //删除按钮 
    async deleteUser(row){
        //console.log(row);
        let confirm = await this.$myconfirm('确认删除该员工吗?');
        if(confirm){
            let res = await deleteUserApi({userId:row.userId});
            //删除成功，刷新列表
            if(res && res.code == 200){
                this.getUserList();
                 //提示信息
                this.$message.success(res.msg);
            }
        }
    },
    //编辑按钮
    editUser(row){
        //清空表单数据
        this.$resetForm("addForm", this.addModel);
        //0：新增，1：编辑
        this.addModel.type = "0";
        //把当前编辑的数据回显到表单
        this.$objCoppy(row,this.addModel)
        this.dialog.visible = true;
        //console.log(row);
    },
     //新增按钮事件
    addUser() {
      //清空表单数据
      this.$resetForm("addForm", this.addModel);
      this.dialog.title = "新增员工";
      this.dialog.visible = true;
      //0：新增，1：编辑
      this.addModel.type = "0";
    },
    //表格是否启用点击事件
    async changeUsed(row) {
      //console.log(row);
      let parm = {
        userId: row.userId,
        isUsed: row.isUsed
      }
      let res = await editUserApi(parm);
      if(res && res.code == 200){
        //刷新列表
        this.getUserList();
        //提示信息
        this.$message.success(res.msg);
      }
    },
    //表格是否离职点击事件
    async changeStatus(row) {
      //console.log(row);
      let parm = {
        userId: row.userId,
        status: row.status
      }
      let res = await editUserApi(parm);
      if(res && res.code == 200){
        //刷新列表
        this.getUserList();
        //提示信息
        this.$message.success(res.msg);
      }

    },
    //对话框确实事件
    onConfirm() {
      this.$refs.addForm.validate(async (valid) => {
        if (valid) {
          let res = null;
          if (this.addModel.type == "0") {
            //新增
            res = await addUserApi(this.addModel);
          }else{
            //编辑
            res = await editUserApi(this.addModel);
          }
          //请求成功，刷新员工列表
          if (res && res.code == 200) {
            this.getUserList();
            //关闭弹窗
            this.dialog.visible = false;
            //提示信息
            this.$message.success(res.msg);
          }
        }
      });
    },
    //对话框关闭事件
    onClose() {
      this.dialog.visible = false;
    },
    // 获取用户列表
    async getUserList() {
      let res = await getUserListApi(this.parm);
      if (res.code == 200) {
        //console.log(res);
        this.tableList = res.data.records;
        this.parm.total = res.data.total;
      }
    },

    //页容量改变时触发
    sizeChange(val) {
        this.parm.pageSize = val;
        this.getUserList();
    },
    //页数改变时触发
    currentChange(val) {
        this.parm.currentPage  = val;
        this.getUserList();
    },
  },
};
</script>

<style lang="scss" scoped>
</style>