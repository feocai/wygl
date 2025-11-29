<template>
  <el-main>
    <!-- 搜索框 -->
    <el-form
      :model="params"
      ref="searchForm"
      label-width="80px"
      :inline="true"
      size="small"
    >
      <el-form-item label="栋数名称">
        <el-input v-model="params.buildName"></el-input>
      </el-form-item>
      <el-form-item label="单元名称">
        <el-input v-model="params.unitName"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button icon="el-icon-search" @click="searchBtn"
          >查询</el-button
        >
        <el-button style="color: #ff7670" icon="el-icon-close" @click="resetBtn"
          >重置</el-button
        >
        <el-button type="primary" icon="el-icon-plus" @click="addBtn"
          >新增</el-button
        >
      </el-form-item>
    </el-form>
    <!-- 表格 -->
    <el-table :data="tableList" border stripe :height="tableHeight" size="small">
      <el-table-column prop="name" label="栋数名称"></el-table-column>
      <el-table-column prop="unitName" label="单元名称"></el-table-column>
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button type="primary" size="small" @click="editBtn(scope.row)"
            >编辑</el-button
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
      :current-page.sync="params.currentPage"
      :page-sizes="[10, 20, 40, 80, 100]"
      :page-size="params.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="params.total"
      background
    >
    </el-pagination>
    <!-- 新增或编辑弹框 -->
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
         <el-form-item label="所属栋数" prop="buildId">
          <el-select v-model="addModel.buildId">
            <el-option
                v-for="item in buildTableList"
                :key="item.buildId"
                :label="item.name"
                :value="item.buildId">
            </el-option>
          </el-select>
          </el-form-item>
          <el-form-item label="单元名称" prop="unitName">
            <el-input v-model="addModel.unitName"></el-input>
          </el-form-item>
          <el-form-item>
          </el-form-item>
        </el-form>
      </template>
    </sys-dialog>
  </el-main>
</template>
<script>
import { getListApi, addApi, editApi, deleteApi ,getBuildListApi} from "@/api/houseUnit";
import SysDialog from "@/components/system/SysDialog.vue";
export default {
  components: { SysDialog },
  data() {
    return {
      //栋数列表
      buildTableList:[],
      //新增或编辑数据
      addModel: {
        editType: "", //0：新增，1：编辑
        unitId: "",
        buildId: "",
        unitName: "",
      },
      //表格高度
      tableHeight: 0,
      addDialog: {
        title: "",
        width: 610,
        height: 150,
        visible: false,
      },
      //校验规则
      rules: {
        buildId:[{
            trigger:'change',
            required: true,
            message:'请选择所属栋数',
        }],
        unitName:[{
            trigger:'change',
            required:true,
            message:'请填写单元名称',
        }],
      },
      //列表数据
      tableList: [],
      //查询参数
      params: {
        unitId: "", //房屋单元id
        buildName: "", //栋数名称
        unitName: "", //单元名称
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
    //获取栋数列表
    async getBuildList(){
        let res = await getBuildListApi();
        if(res && res.code == 200){
            this.buildTableList = res.data;
            console.log(res);
        }
    },
    //弹窗确认事件
    onConfirm() {
      this.$refs.addForm.validate( async valid=>{
        if(valid){
            let res = null;
            if(this.addModel.editType == '0'){ // 新增
                 res = await addApi(this.addModel);
            }else{//修改
                res = await editApi(this.addModel);
            }
            if(res && res.code == 200){
                //刷新列表，提示信息，关闭弹窗
                this.getList();
                this.$message.success(res.msg);
                this.addDialog.visible = false;
            }
        }
      })

    },
    //弹窗关闭事件
    onClose() {
      this.addDialog.visible = false;
    },
    //当前页改变时触发
    currentChange(val) {
      this.params.currentPage = val;
      this.getList();
    },
    //页容量改变时触发
    sizeChange(val) {
      this.params.pageSize = val;
      this.getList();
    },
    //删除按钮
    async deleteBtn(row) {
      let confirm = await this.$myconfirm("确定要删除吗?");
      if (confirm) {
        let res = await deleteApi({ unitId: row.unitId });
        if (res && res.code == 200) {
          //刷新列表，提示信息
          this.getList();
          this.$message.success(res.msg);
        }
      }
    },
    //编辑按钮
    editBtn(row) {
      //清空表单
      this.$resetForm('addForm',this.addModel);
      //设置编辑属性
      this.addModel.editType ='1'
      //设置弹窗属性
      this.addDialog.title = '编辑房屋单元';
      this.addDialog.visible = true;
      //把当前需要编辑的数据放到数据域
      this.$objCoppy(row,this.addModel);
    },
    //新增按钮
    addBtn() {
      //清空表单
      this.$resetForm('addForm',this.addModel);
      //设置弹窗属性
      this.addModel.editType ='0'
      //为弹窗属性赋值
      this.addDialog.title = "新增房屋单元";
      this.addDialog.visible = true;
    },
    //重置按钮
    resetBtn() {
      this.params.buildName = "";
      this.params.unitName = "";
      this.getList();
    },
    //搜索按钮
    searchBtn() {
      this.getList();
    },
    //查询列表
    async getList() {
      let res = await getListApi(this.params);
      if (res && res.code == 200) {
        this.tableList = res.data.records;
        this.params.total = res.data.total;
      }
    },
  },
};
</script>

<style lang="scss" scoped>
</style>
