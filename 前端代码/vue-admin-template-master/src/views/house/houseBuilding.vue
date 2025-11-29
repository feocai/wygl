<template>
  <el-main>
    <!-- 搜索框 -->
    <el-form
      :model="parm"
      ref="form"
      label-width="80px"
      :inline="true"
      size="small"
    >
      <el-form-item label="栋数名称">
        <el-input v-model="parm.name"></el-input>
      </el-form-item>
      <el-form-item label="栋数类型">
        <el-select v-model="parm.type" placeholder="" clearable filterable>
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button @click="searchBtn" icon="el-icon-search"
          >查询</el-button
        >
        <el-button style="color: #ff7670" icon="el-icon-close" @click="resetBtn">重置</el-button>
        <el-button type="primary" icon="el-icon-plus" @click="addBtn">新增</el-button>
      </el-form-item>
    </el-form>
    <!-- 表格列表 -->
    <el-table :data="tableList" border stripe :height="tableHeight" size="small">
      <el-table-column prop="name" label="栋数名称"></el-table-column>
      <el-table-column prop="type" label="栋数类型">
        <template slot-scope="scope">
          <el-tag type="success" size="small" v-if="scope.row.type == '0'">
            普通房</el-tag
          >
          <el-tag type="danger" size="small" v-if="scope.row.type == '1'">
            电梯房</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column prop="orderNum" label="序号"></el-table-column>
      <el-table-column label="操作" align="center" width="200">
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
      :pager-count="7">
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
            <el-form :model="addModel" ref="addRef" :rules="rules" label-width="80px" :inline="true" size="small">
                <el-form-item label="栋数名称" prop="name">
                    <el-input v-model="addModel.name"></el-input>
                </el-form-item>
    
                 <el-form-item label="栋数类型" prop="type">
                   <el-select v-model="addModel.type">
                    <el-option v-for="item in options"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                    </el-option>
                   </el-select>
                </el-form-item>
                <el-form-item label="栋数序号">
                    <el-input v-model="addModel.orderNum" type="number"></el-input>
                </el-form-item>
            </el-form>
        </template>
    </sys-dialog>
  </el-main>
</template>
<script>
import { getListApi ,addApi,editApi,deleteApi} from "@/api/building";
import SysDialog from '@/components/system/SysDialog.vue';
export default {
  components: { SysDialog },
  data() {
    return {
        //表单验证规则
        rules:{
            name:[{
                trigger:'change',
                required:true,
                message:'请填写栋数名称'
            }],
            type:[{
                trigger:'change',
                required:true,
                message:'请选择栋数类型'
            }]
        },
      //新增或编辑表单数据
      addModel:{
        buildId:'',  //栋数id
        editType:'', //0:新增，1：编辑
        name:'',     //栋数名称
        type:'',     //栋数类型  0：普通房，1：电梯房
        orderNum:'', //序号
      },
      //定义弹窗属性
      addDialog:{
        title:"",
        height:200,
        width:610,
        visible:false
      },
      //表格高度
      tableHeight: 0,
      options: [
        {
          value: "0",
          label: "普通房",
        },
        {
          value: "1",
          label: "电梯房",
        },
      ],
      //存放列表的数据
      tableList: [],
      //获取列表的参数
      parm: {
        name: "",       //栋数名称
        type: "",       //栋数类型  0：普通房，1：电梯房
        pageSize: 10,   //页容量
        currentPage: 1, //当前页
        total: 0,
      },
    };
  },
  created() {
    this.getList();
  },
  mounted() {
    this.$nextTick(() => {
      this.tableHeight = window.innerHeight - 178;
    });
  },
  methods: {
    //新增或编辑确认事件
    onConfirm(){
        this.$refs.addRef.validate(async valid=>{
            if(valid){
                let res =null;
                if(this.addModel.editType == '0'){
                     res= await addApi(this.addModel);
                }else{
                    res = await editApi(this.addModel);
                }
                if(res && res.code == 200){
                    //刷新表格，提示信息，关闭弹窗
                    this.getList();
                    this.$message.success(res.msg);
                    this.addDialog.visible=false
                }
            }
        })
     
    },
    //新增或编辑弹窗关闭
    onClose(){
        this.addDialog.visible=false
    },
    //新增按钮
    addBtn(){
        //清空表单
        this.$resetForm('addRef',this.addModel);
        //弹窗属性
        this.addModel.editType = '0'; //新增
        this.addDialog.visible=true;
        this.addDialog.title='新增栋数';

    },
    //重置按钮
    resetBtn(){
        this.parm.name ='';
        this.parm.type='';
        this.getList();
    },
    //删除按钮
    async deleteBtn(row) {
        let confirm = await this.$myconfirm('确认删除该数据吗?');
        if(confirm){
            let res = await deleteApi({buildId:row.buildId});
            if(res && res.code == 200){
                //刷新页面，提示信息
                this.getList();
                this.$message.success(res.msg);
            }
        }
    },

    //编辑按钮
    editBtn(row) {
        //清空表单
        this.$resetForm('addRef',this.addModel);
        //编辑弹窗
        this.addModel.editType = '1'; //编辑
        //设置弹窗属性
        this.addDialog.title = '编辑栋数';
        this.addDialog.visible = true;
        //设置需要编辑的数据
        this.$objCoppy(row,this.addModel);
    },
    //页数改变时触发
    currentChange() {},
    //页容量改变时触发
    sizeChange() {},
    //搜索按钮
    searchBtn() {
      this.getList();
    },
    //获取列表
    async getList() {
      let res = await getListApi(this.parm);
      if (res && res.code == 200) {
        this.tableList = res.data.records;
        this.parm.total = res.data.total;
        //console.log(res);
      }
    },
  },
};
</script>

<style lang="scss" scoped>
</style>