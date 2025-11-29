<template>
  <el-main>
    <!-- 搜索框 -->
    <el-form
      :model="parm"
      ref="parmForm"
      label-width="80px"
      :inline="true"
      size="small"
    >
      <el-form-item label="车位名称">
        <el-input v-model="parm.parkName"></el-input>
      </el-form-item>
      <el-form-item label="车位类型">
        <el-select v-model="parm.parkType" placeholder="请选择">
          <el-option
            v-for="item in parkTypeOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="使用状态">
        <el-select v-model="parm.parkStatus" placeholder="请选择">
          <el-option
            v-for="item in parkStatusOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button @click="searchBtn" icon="el-icon-search">查询</el-button>
        <el-button @click="resetBtn" icon="el-icon-close" style="color: #ff7670"
          >重置</el-button
        >
        <el-button @click="addBtn" icon="el-icon-plus" type="primary"
          >新增</el-button
        >
      </el-form-item>
    </el-form>
    <!-- 表格 -->
    <el-table :data="tableList" border stripe :height="tableHeight" size="small">
      <el-table-column label="车位名称" prop="parkName"></el-table-column>
      <el-table-column label="车位类型" prop="parkType">
        <template slot-scope="scope">
          <el-tag type="warning" size="small" v-if="scope.row.parkType == '0'"
            >地上</el-tag
          >
          <el-tag type="success" size="small" v-if="scope.row.parkType == '1'"
            >地下</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column label="车位状态" prop="parkStatus">
        <template slot-scope="scope">
          <el-tag size="small" v-if="scope.row.parkStatus == '0'"
            >未使用</el-tag
          >
          <el-tag type="success" size="small" v-if="scope.row.parkStatus == '1'"
            >已使用</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column label="序号" prop="parkNum"></el-table-column>
      <el-table-column label="操作" width="200" align="center">
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
          <el-form-item label="车位名称" prop="parkName">
            <el-input v-model="addModel.parkName"></el-input>
          </el-form-item>

          <el-form-item label="车位类型" prop="parkType">
            <el-select v-model="addModel.parkType" placeholder="请选择">
              <el-option
                v-for="item in parkTypeOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="使用状态" prop="parkStatus">
            <el-select v-model="addModel.parkStatus" placeholder="请选择">
              <el-option
                v-for="item in parkStatusOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="车位序号">
            <el-input v-model="addModel.parkNum"></el-input>
          </el-form-item>
        </el-form>
      </template>
    </sys-dialog>
  </el-main>
</template>
<script>
import { getListApi, addApi, editApi, deleteApi } from "@/api/parkList";
import SysDialog from "@/components/system/SysDialog.vue";
export default {
  components: { SysDialog },
  data() {
    return {
        //编辑状态
        editType:'',//0：新增，1编辑
      //校验规则
      rules: {
        parkName:[{
            trigger:'change',
            required:true,
            message:'请填写车位名称',
        }],
        parkType:[{
            trigger:'change',
            required: true,
            message:'请选择车位类型',
        }],
        parkStatus:[{
            trigger:'change',
            required:true,
            message:'请选择使用状态'
        }],

      },
      //新增或编辑弹窗数据
      addModel: {
        //车位id
        parkId:'',
        //车位名称
        parkName: "",
        //车位类型
        parkType: "",
        //车位状态
        parkStatus: "",
        // 车位序号
        parkNum: "",
      },
      //弹窗属性
      addDialog: {
        title: "",
        width: 610,
        height: 300,
        visible: false,
      },
      tableHeight: 0,
      //使用状态下拉框
      parkStatusOptions: [
        {
          value: "0",
          label: "未使用",
        },
        {
          value: "1",
          label: "已使用",
        },
      ],
      //车位类型下拉框
      parkTypeOptions: [
        {
          value: "0",
          label: "地上",
        },
        {
          value: "1",
          label: "地下",
        },
      ],
      //表格数据
      tableList: [],
      //搜索参数
      parm: {
        parkName: "", //车位名称
        parkType: "", //车位类型 0：地上，1：地下
        parkStatus: "", //使用状态 0：未使用，1：已使用
        currentPage: 1, //当前页
        pageSize: 10, //每页多少条
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
  },
  methods: {
    //弹窗确认事件
    onConfirm() {
        this.$refs.addForm.validate( async valid=>{
            if(valid){
                let res = null;
                if(this.editType == '0'){//新增
                    res = await addApi(this.addModel);
                }else{//编辑
                    res = await editApi(this.addModel);
                }
                if(res && res.code == 200){
                    //刷新页面，提示信息，关闭弹窗
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
    //页数改变时触发
    currentChange(val) {
        this.parm.currentPage = val;
    },
    //页容量改变时触发
    sizeChange(val) {
        this.parm.pageSize = val;
    },
    //删除按钮
    async deleteBtn(row) {
        let confirm = await this.$myconfirm('确定要删除该数据吗');
        if(confirm){
            let res = await deleteApi({parkId:row.parkId});
            if(res && res.code == 200){
                //刷新页面，提示信息
                this.getList();
                this.$message.success(res.msg);
            }
        }

    },
    //编辑按钮
    editBtn(row) {
        //清空数据
       this.$resetForm('addForm',this.addModel);
       //设置弹窗属性
       this.addDialog.title = '编辑车位';
       this.addDialog.visible = true;
        //设置编辑状态
        this.editType = '1';
        //将编辑的数据赋值到数据域
        this.$objCoppy(row,this.addModel);
    },
    //新增按钮
    addBtn() {
       //清空数据
       this.$resetForm('addForm',this.addModel);
       //设置弹窗属性
       this.addDialog.title = '新增车位';
       this.addDialog.visible = true;
       //设置编辑状态
        this.editType = '0';
        
    },
    //重置按钮
    resetBtn() {
      this.parm.parkName = "";
      this.parm.parkType = "";
      this.parm.parkStatus = "";
      this.getList();
    },
    // 搜索按钮
    searchBtn() {
      this.getList();
    },
    //分页查询
    async getList() {
      let res = await getListApi(this.parm);
      //console.log(res);
      if (res && res.code == 200) {
        this.tableList = res.data.records;
        this.parm.total = res.data.total;
      }
    },
  },
};
</script>

<style lang="scss" scoped>
</style>