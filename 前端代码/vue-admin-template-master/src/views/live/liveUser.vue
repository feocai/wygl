<template>
  <el-main>
    <!-- 搜索框 -->
    <el-form
      :model="param"
      ref="refForm"
      label-width="80px"
      :inline="true"
      size="small"
    >
      <el-form-item label="姓名">
        <el-input v-model="param.loginName"></el-input>
      </el-form-item>
      <el-form-item label="电话">
        <el-input v-model="param.phone"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button icon="el-icon-search" @click="searchBtn">查询</el-button>
        <el-button icon="el-icon-close" style="color: #ff7670" @click="resetBtn"
          >重置</el-button
        >
        <el-button type="primary" @click="addBtn">新增</el-button>
      </el-form-item>
    </el-form>
    <!-- 表格 -->
    <el-table :data="tableList" border stripe :height="tableHeight" size="small">
      <el-table-column label="业主姓名" prop="loginName"></el-table-column>
      <el-table-column label="性别" prop="sex">
        <template slot-scope="scope">
          <el-tag type="success" size="small" v-if="scope.row.sex == '0'"
            >男</el-tag
          >
          <el-tag type="danger" size="small" v-if="scope.row.sex == '1'"
            >女</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column label="电话" prop="phone"></el-table-column>
      <el-table-column label="栋数" prop="name"></el-table-column>
      <el-table-column label="单元" prop="unitName"></el-table-column>
      <el-table-column label="房屋编号" prop="houseNum"></el-table-column>
      <el-table-column label="使用面积" prop="houseArea"></el-table-column>
      <el-table-column label="车位" prop="parkName"></el-table-column>
      <!-- <el-table-column label="是否启用" prop="status">
                <template slot-scope="scope">
                    <el-tag type="success" size="small" v-if="scope.row.status == '0'">启用</el-tag>
                    <el-tag type="danger" size="small" v-if="scope.row.status == '1'">禁用</el-tag>
                </template>
            </el-table-column> -->
      <el-table-column label="操作" width="480" align="center">
        <template slot-scope="scope">
          <el-button type="success" size="small" @click="editBtn(scope.row)"
            >编辑</el-button
          >
          <el-button type="primary" size="small" @click="assignHouse(scope.row)"
            >分配房屋</el-button
          >
          <el-button type="success" size="small" @click="assignPark(scope.row)"
            >分配车位</el-button
          >
          <el-button type="danger" size="small" @click="liveHouse(scope.row)"
            >退房</el-button
          >
          <el-button type="warning" size="small" @click="livePark(scope.row)"
            >退车位</el-button
          >
          <el-button type="danger" size="small" @click="deleteBtn(scope.row)"
            >删除</el-button
          >
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
      :total="param.total"
      background
    >
    </el-pagination>
    <!-- 弹窗 -->
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
          <el-form-item label="业主姓名" prop="loginName">
            <el-input v-model="addModel.loginName"></el-input>
          </el-form-item>
          <el-form-item label="性别" prop="sex">
            <el-radio-group v-model="addModel.sex">
              <el-radio :label="'0'">男</el-radio>
              <el-radio :label="'1'">女</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="电话" prop="phone">
            <el-input v-model="addModel.phone"></el-input>
          </el-form-item>
          <el-form-item label="角色" prop="roleId">
            <el-select v-model="addModel.roleId" placeholder="请选择">
              <el-option
                v-for="item in roleList"
                :key="item.roleId"
                :label="item.roleName"
                :value="item.roleId"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="登录账户" prop="username">
            <el-input v-model="addModel.username"></el-input>
          </el-form-item>
          <el-form-item
            label="登录密码"
            prop="password"
            v-if="addModel.editType != '1'"
          >
            <el-input v-model="addModel.password"></el-input>
          </el-form-item>
          <el-form-item label="状态" prop="status">
            <el-radio-group v-model="addModel.status">
              <el-radio :label="'0'">启用</el-radio>
              <el-radio :label="'1'">禁用</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-form>
      </template>
    </sys-dialog>
    <!-- 分配房屋弹窗 -->
    <sys-dialog
      :title="assignHouseDialog.title"
      :height="assignHouseDialog.height"
      :width="assignHouseDialog.width"
      :visible="assignHouseDialog.visible"
      @onClose="assignHouseClose"
      @onConfirm="assignHouseConfirm"
    >
      <template slot="content">
        <el-main style="padding: 0px">
          <!-- 搜索框 -->
          <el-form
            :model="houseParam"
            label-width="80px"
            :inline="true"
            size="small"
          >
            <el-form-item label="栋数名称">
              <el-input
                v-model="houseParam.buildName"
                placeholder="请输入栋数名称"
              ></el-input>
            </el-form-item>
            <el-form-item label="单元名称">
              <el-input
                v-model="houseParam.unitName"
                placeholder="请输入单元名称"
              ></el-input>
            </el-form-item>
            <el-form-item label="房屋编号">
              <el-input
                v-model="houseParam.houseNum"
                placeholder="请输入房屋编号"
              ></el-input>
            </el-form-item>
            <el-button
              size="small"
              @click="assignHouseSearchBtn"
              icon="el-icon-search"
              >搜索</el-button
            >
            <el-button
              size="small"
              @click="assignHouseResetBtn"
              icon="el-icon-close"
              style="color: #ff7670"
              >重置</el-button
            >
          </el-form>
          <!-- 表格 -->
          <el-table :data="houseList" border stripe :height="houseTableHeight">
            <el-table-column label="选择" width="50" align="center">
              <template slot-scope="scope">
                <el-radio
                  v-model="assignHouseParm.houseId"
                  :label="scope.row.houseId"
                  @change="getCurrentRow(scope.row)"
                >
                  {{ "" }}
                </el-radio>
              </template>
            </el-table-column>
            <el-table-column
              prop="name"
              label="栋数名称"
              align="center"
            ></el-table-column>
            <el-table-column
              prop="unitName"
              label="单元名称"
              align="center"
            ></el-table-column>
            <el-table-column
              prop="houseNum"
              label="房屋编号"
              align="center"
            ></el-table-column>
            <el-table-column
              prop="houseArea"
              label="使用面积"
              align="center"
            ></el-table-column>
            <el-table-column prop="status" label="分配状态" align="center">
              <template slot-scope="scope">
                <el-tag
                  type="success"
                  size="small"
                  v-if="scope.row.status == '0'"
                  >未分配</el-tag
                >
                <el-tag
                  type="danger"
                  size="small"
                  v-if="scope.row.status == '1'"
                  >已分配</el-tag
                >
              </template>
            </el-table-column>
          </el-table>
          <!-- 房屋列表分页 -->
          <el-pagination
            @size-change="assignHouseSizeChange"
            @current-change="assignHouseCurrentChange"
            :current-page.sync="houseParam.currentPage"
            :page-sizes="[5,10, 15, 20, 25]"
            :page-size="houseParam.pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="houseParam.total" background>
          </el-pagination>
        </el-main>
      </template>
    </sys-dialog>
    <!-- 分配车位 -->
    <sys-dialog
    :title="parkDialog.title"
    :height="parkDialog.height"
    :width="parkDialog.width"
    :visible="parkDialog.visible"
    @onClose="parkOnClose"
    @onConfirm="parkOnConfirm"
    >
    <template slot="content">
        <el-main style="padding:0px">
            <!-- 搜索框 -->
            <el-form :model="parkParm" label-width="80px" :inline="true" size="small">
                <el-form-item label="车位名称">
                    <el-input v-model="parkParm.parkName"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button @click="parkSearchBtn" icon="el-icon-search">查询</el-button>
                    <el-button @click="parkResetBtn" icon="el-icon-close" style="color: #ff7670">重置</el-button >
                </el-form-item>
            </el-form>
            <!-- 表格 -->
            <el-table :data="parkList" border stripe :height="parkTableHeight">
                <el-table-column label="选择" width="50" align="center">
                    <template slot-scope="scope">
                        <el-radio
                        v-model="assignParkParm.parkId"
                        :label="scope.row.parkId"
                        @change="getCurrentParkRow(scope.row)"
                        >
                        {{ "" }}
                        </el-radio>
                    </template>
                </el-table-column>
                <el-table-column label="车位名称" prop="parkName"></el-table-column>
                <el-table-column label="车位类型" prop="parkType">
                    <template slot-scope="scope">
                    <el-tag type="warning" size="small" v-if="scope.row.parkType == '0'">地上</el-tag>
                    <el-tag type="success" size="small" v-if="scope.row.parkType == '1'">地下</el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="车位状态" prop="parkStatus">
                    <template slot-scope="scope">
                        <el-tag size="small" v-if="scope.row.parkStatus == '0'">未使用</el-tag>
                        <el-tag type="success" size="small" v-if="scope.row.parkStatus == '1'">已使用</el-tag>
                    </template>
                </el-table-column>
            </el-table>
      <!-- 车位分页 -->
        <el-pagination
            @size-change="parkSizeChange"
            @current-change="parkCurrentChange"
            :current-page.sync="parkParm.currentPage"
            :page-sizes="[5,10, 15, 20, 25]"
            :page-size="parkParm.pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="parkParm.total" background>
        </el-pagination>
    </el-main>
    </template>
    </sys-dialog>
  </el-main>
</template>
<script>
import {
  getListApi,
  saveLiveUserApi,
  editLiveUserApi,
  deleteLiveUserApi,
  getRoleListApi,
  getUserByIdApi,
  getHouseListApi,
  saveAssignHouseApi,
  getParkListApi,
  assignParkApi,
  returnHouseApi,
  returnParkApi
} from "@/api/liveUser";
import SysDialog from "@/components/system/SysDialog.vue";

export default {
  components: { SysDialog },
  data() {
    return {
        //分配车位参数
        assignParkParm:{
            parkId:'',
            userId:'',
        },
        //车位表格高度
        parkTableHeight:0,
        //车位弹窗属性
        parkDialog:{
            title:'',
            height:400,
            width:900,
            visible:false,
        },
        parkList:[],// 车位列表
        //车位查询参数
        parkParm: {
            parkName: "", //车位名称
            parkType: "", //车位类型 0：地上，1：地下
            parkStatus: "0", //使用状态 0：未使用，1：已使用
            currentPage: 1, //当前页
            pageSize: 10, //每页多少条
            total: 0, //总条数
        },
        //分配房屋列表表格高度
        houseTableHeight:0,
      //分配房屋提交的数据
      assignHouseParm:{
        houseId: "", //房屋id
        userId:'',//业主id

      },
      //分配房屋弹窗属性
      assignHouseDialog: {
        title: "",
        height: 400,
        width: 1050,
        visible: false,
      },
      //房屋数据域
      houseList: [],
      //获取房屋参数
      houseParam: {
        houseId: "", //房屋id
        buildName: "", //栋数名称
        unitName: "", //单元名称
        houseNum: "", //房屋编号
        currentPage: 1, //当前第几页
        pageSize: 5, //页容量
        status: "0", //使用状态 0：未使用， 1：已使用
        total: 0, //总条数
      },
      //角色列表
      roleList: [],
      //校验规则
      rules: {
        loginName: [
          {
            trigger: "change",
            required: true,
            message: "请填写姓名",
          },
        ],
        sex: [
          {
            trigger: "change",
            required: true,
            message: "请填选择性别",
          },
        ],
        phone: [
          {
            trigger: "change",
            required: true,
            message: "请填写电话",
          },
        ],
        username: [
          {
            trigger: "change",
            required: true,
            message: "请填写登录账号",
          },
        ],
        password: [
          {
            trigger: "change",
            required: true,
            message: "请填写密码",
          },
        ],
        status: [
          {
            trigger: "change",
            required: true,
            message: "请选择账户状态",
          },
        ],
        roleId: [
          {
            trigger: "change",
            required: true,
            message: "请选择角色",
          },
        ],
      },
      //弹窗参数
      addModel: {
        editType: "", //标识 0：新增，1：编辑
        userId: "", //业主id
        // userName: "", //姓名
        loginName: "", //姓名
        sex: "", //性别
        phone: "", //电话
        username: "", //登录名
        password: "", //密码
        status: "", //状态
        roleId: "", //角色id
        houseNum: "", //房屋编号
        name: "", //栋数名称
        houseArea: "", //房屋面积
        unitName: "", //单元名称
        parkName: "", //车位名称
        houseId: "", //房屋id
        parkId: "", //车位id
      },
      //弹窗属性
      addDialog: {
        title: "",
        height: 240,
        width: 620,
        visible: false,
      },
      //表格高度
      tableHeight: 0,
      //表格数据
      tableList: [],
      //查询参数
      param: {
        loginName: "", //姓名
        phone: "", //电话
        currentPage: 1, //当前第几页
        pageSize: 10, //页容量
        total: 0,
      },
    };
  },
  mounted() {
    this.$nextTick(() => {
      this.tableHeight = window.innerHeight - 178;
      this.houseTableHeight = window.innerHeight - 410;
      this.parkTableHeight = window.innerHeight - 410;
    });
  },
  created() {
    this.getList();
    this.getRoleList();
  },
  methods: {
    //分配车位点击事件
    getCurrentParkRow(row){
        this.assignParkParm.parkId = row.parkId;
        //console.log(row);
    },
    //页数改变时触发
    parkCurrentChange(){
        this.parkParm.currentPage = val;
        this.getAssignHouseList();
    },
    //页容量改变时触发
    parkSizeChange(val){
        this.parkParm.sizeChange = val;
        this.getAssignHouseList();
    },
    //车位列表重置按钮
    parkResetBtn(){
        this.parkParm.parkName = '';
        this.getParkList();
    },
    //车位列表查询按钮
    parkSearchBtn(){
        this.getParkList();
    },
    //车位弹窗确认事件
    async parkOnConfirm(){
        if(!this.assignParkParm.parkId){
            this.$message.warning('请选择车位！');
            return;
        }
         let res = await assignParkApi(this.assignParkParm);
         if(res && res.code == 200){
            //关闭弹窗，刷新页面，提示信息
            this.parkDialog.visible = false;
            this.getList();
            this.$message.success(res.msg);
         }
    },
    //车位弹窗关闭事件
    parkOnClose(){
        //关闭弹窗
        this.parkDialog.visible = false;
    },
    //退车位按钮
    async livePark(row) {
      console.log(row);
      if(!row.parkId){
          this.$message.warning('该租户暂时无可退的车位');
          return;
      }
      let confirm = await this.$myconfirm('确定要退车位吗');
      if(confirm){
          let parm= {
              parkId:row.parkId,
              userId:row.userId
          };
          let res = await returnParkApi(parm);
          if(res && res.code == 200){
            //刷新页面，提示信息
            this.getList();
            this.$message.success(res.msg);
          }
      }
    },
    //退房按钮
    async liveHouse(row) {
      console.log(row);
      if(!row.houseId){
          this.$message.warning('该租户暂时无可退的房屋');
          return;
      }
      let confirm = await this.$myconfirm('确定要退房吗');
      if(confirm){
          let parm= {
              houseId:row.houseId,
              userId:row.userId
          };
          let res = await returnHouseApi(parm);
          if(res && res.code == 200){
            //刷新页面，提示信息
            this.getList();
            this.$message.success(res.msg);
          }
      }
    },
    //分配车位按钮
    assignPark(row) {
        //console.log(row);
        //清空数据
        if(row.parkName){
            this.$message.warning('分配车位，不能重复');
            return;
        }
        this.assignParkParm.userId = '';
        this.assignParkParm.parkId = '';
        this.parkParm.parkName = '';
        this.assignParkParm.userId = row.userId;
        //获取车位的列表
        this.getParkList();
        //设置弹窗属性
        this.parkDialog.title = '为【' + row.userName + '分配车位';
        this.parkDialog.visible = true;
    },
    //分配房屋页数改变时触发
    assignHouseCurrentChange(val){
        this.houseParam.currentPage = val;
        this.getAssignHouseList();
    },
    //分配房屋页容量改变时触发
    assignHouseSizeChange(val){
        this.houseParam.pageSize = val;
        this.getAssignHouseList();
    },
    //选择房屋事件
    getCurrentRow(row) {
     // console.log(row);
      this.assignHouseParm.houseId = row.houseId;
    },
    //重置按钮
    assignHouseResetBtn() {
        //清空搜索框的数据
        this.houseParam.buildName = '';
        this.houseParam.unitName = '';
        this.houseParam.houseNum = '';
        this.getAssignHouseList();
    },
    //搜索按钮
    assignHouseSearchBtn() {
        this.getAssignHouseList();
    },
    //分配房屋确认事件
    async assignHouseConfirm() {
       //判断是否选中数据
      if(!this.assignHouseParm.houseId){
        this.$message.warning('请选择分配的房屋')
        return;
      }
        //数据提交
        let res = await saveAssignHouseApi(this.assignHouseParm);
        if(res && res.code == 200){
            //关闭弹窗，提示信息，刷新列表
            this.assignHouseDialog.visible = false;
            this.$message.success(res.msg);
            this.getList();
        }
    },
    //分配房屋关闭事件
    assignHouseClose() {
      //设置弹窗属性
      this.assignHouseDialog.visible = false;
    },
    //分配房屋按钮
    async assignHouse(row) {
        if(row.houseNum){
            this.$message.warning('已经分配房屋，不能重新分配');
            return;
        }
      //console.log(row);
      //清空搜索框的数据
      this.houseParam.buildName = '';
      this.houseParam.unitName = '';
      this.houseParam.houseNum = '';
      //清空单选按钮
      this.assignHouseParm.houseId = '';
      this.assignHouseParm.userId = '';
      //获取未分配的房屋
      this.getAssignHouseList();
      //弹窗属性设置
      this.assignHouseDialog.visible = true;
      this.assignHouseDialog.title = "为【" + row.userName + "】分配房屋";
      //设置分配人
      this.assignHouseParm.userId = row.userId;

    },
    //弹窗确认
    onConfirm() {
      this.$refs.addForm.validate(async (valid) => {
        if (valid) {
          let res = null;
          if (this.addModel.editType == "0") {
            //新增
            res = await saveLiveUserApi(this.addModel);
          } else {
            //编辑
            res = await editLiveUserApi(this.addModel);
          }
          if (res && res.code == 200) {
            //刷新页面,提示信息，关闭弹窗
            this.getList();
            this.$message.success(res.msg);
            this.addDialog.visible = false;
          }
        }
      });
    },
    
    //获取未分配的房屋
    async getAssignHouseList(){
      let res = await getHouseListApi(this.houseParam);
      if (res && res.code == 200) {
        //把返回的数据放到房屋的数据域
        //console.log(row);
        this.houseList = res.data.records;
        this.houseParam.total = res.data.total;
      }
    },
    //弹窗关闭
    onClose() {
      this.addDialog.visible = false;
    },
    //当页数改变时触发
    currentChange(val) {
      this.param.currentPage = val;
      this.getList();
    },
    //页容量改变时触发
    sizeChange(val) {
      this.param.pageSize = val;
      this.getList();
    },
    //删除按钮
    deleteBtn(row) {},
    //编辑按钮
    async editBtn(row) {
      //清空表单
      this.$resetForm("addForm", this.addModel);
      //将编辑的数据赋值到文本域
      let res = await getUserByIdApi({ userId: row.userId });
      if (res && res.code == 200) {
        if (res.data) {
          this.$objCoppy(res.data, this.addModel);
        }
        //console.log(this.addModel);
      }
      //设置编辑标识
      this.addModel.editType = "1";
      //设置弹窗属性
      this.addDialog.title = "编辑业主信息";
      this.addDialog.visible = true;
    },
    //新增
    addBtn() {
      //清空表单
      this.$resetForm("addForm", this.addModel);
      //设置编辑属性
      this.addModel.editType = "0";
      //设置弹窗属性
      this.addDialog.visible = true;
      this.addDialog.title = "新增业主";
    },
    //重置
    resetBtn() {
      this.param.loginName = "";
      this.param.phone = "";
      this.getList();
    },

    //搜索
    searchBtn() {
      this.getList();
    },
    //查询表格数据
    async getList() {
      let res = await getListApi(this.param);
      if (res && res.code == 200) {
        console.log("表格数据");
        console.log(res);
        this.tableList = res.data.records;
        this.param.total = res.data.total;
      }
    },
    //查询角色列表
    async getRoleList() {
      let res = await getRoleListApi();
      if (res && res.code == 200) {
        console.log("角色列表");
        console.log(res);
        this.roleList = res.data;
      }
    },
    //查询车位列表
    async getParkList(){
        let res = await getParkListApi(this.parkParm);
        console.log(res);
        if(res && res.code == 200){
            //console.log(res);
            //把数据赋值给车位列表
            this.parkList = res.data.records;
            this.parkParm.total = res.data.total;
        }
    },
  },
};
</script>

<style lang="scss" scoped>
</style>