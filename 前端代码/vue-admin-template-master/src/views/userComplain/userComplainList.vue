<template>
    <el-main>
        <!-- 搜索框 -->
        <el-form :model="parm" ref="searchForm"  label-width="80px" :inline="true" size="small">
            <el-form-item label="投诉标题">
                <el-input v-model="parm.title"></el-input>
            </el-form-item>
             <el-form-item label="投诉内容">
                <el-input v-model="parm.complaintContent"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button icon="el-icon-search" @click="searchBtn">查询</el-button>
                <el-button icon="el-icon-close" style="color:#ff7670" @click="resetBtn">重置</el-button>
                <!-- <el-button type="primary" icon="el-icon-plus" @click="addBtn">新增</el-button> -->
            </el-form-item>
        </el-form>
        <!-- 表格 -->
        <el-table :data="tableList" border stripe :height="tableHeight" size="small">
            <el-table-column label="投诉标题" prop="title"></el-table-column>
            <el-table-column label="投诉内容" prop="complaintContent"></el-table-column>
            <!-- <el-table-column label="投诉时间" prop="createTime"></el-table-column> -->
            <el-table-column label="处理状态" prop="status">
                <template slot-scope="scope">
                    <el-tag type="success" size="small" v-if="scope.row.status == '0'">未处理</el-tag>
                    <el-tag type="warning" size="small" v-if="scope.row.status == '1'">已处理</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="操作" align="center" width="120">
                <template slot-scope="scope">
                    <!-- <el-button type="primary" size="small" icon="el-icon-edit" @click="editBtn(scope.row)">编辑</el-button>
                    <el-button type="danger" size="small" icon="el-icon-delete" @click="deleteBtn(scope.row)">删除</el-button> -->
                    <el-button type="success" size="small" @click="doBtn(scope.row)">处理</el-button>
                </template>
            </el-table-column>
        </el-table>
        <!-- 分页 -->
        <el-pagination
            @size-change="sizeChange"
            @current-change="currentChange"
            :current-page.sync="parm.currentPage"
            :page-sizes="[10,20, 40, 80, 100]"
            :page-size="parm.pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="parm.total" background>
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
            <el-form :model="addModel" ref="addForm" :rules="rules" label-width="80px" :inline="false" size="small">
                <el-form-item label="标题" prop="title">
                    <el-input v-model="addModel.title"></el-input>
                </el-form-item>
                 <el-form-item label="内容" prop="complaintContent">
                    <el-input type="textarea" v-model="addModel.complaintContent"></el-input>
                </el-form-item>
            </el-form>
        </template>
        </sys-dialog>
    </el-main>
</template>

<script>
import {getListApi,addApi,editApi,deleteApi} from '@/api/userComplain'
import {getUserId} from '@/utils/auth'
import SysDialog from '@/components/system/SysDialog.vue'
    export default {
  components: { SysDialog },
        data() {
            return {
                //校验规则
                rules:{
                    title:[{
                        trigger:'change',
                        required:true,
                        message:'请填写投诉标题'
                    }],
                    complaintContent:[{
                        trigger:'change',
                        required:true,
                        message:'请填写投诉内容'
                    }],

                },
                //新增或编辑弹窗属性
                addModel: {
                    complaintId: "",
                    userId: "",
                    editType: "",//0:新增，1：编辑
                    title: "",
                    complaintContent: "",
                },
                //弹框属性
                addDialog:{
                    title:'',
                    width:600,
                    height:180,
                    visible:false,
                },
                tableHeight:0,//表格高度
                tableList:[],//表格数据
                //分页查询参数
                parm:{
                    total:0,//总条数
                    pageSize:10,//页容量
                    currentPage:1,//当前页
                    title:'',//投诉标题
                    complaintContent:'',//投诉内容
                },
            }
        },
        mounted() {
            this.$nextTick(() =>{
                this.tableHeight = window.innerHeight - 178;
            })
        },
        created () {
            this.getList();
        },
        methods: {
            //处理按钮
            async doBtn(row){
                if(row.status == '1'){
                    this.$message.warning('该投诉已经处理，无需重复处理')
                    return;
                }

                let parm = {
                    complaintId:row.complaintId,
                    status:'1'
                };
                let confirm = await this.$myconfirm('确定要处理该投诉吗?');
                if(confirm){
                    let res = await editApi(parm);
                    if(res && res.code == 200){
                        //提示信息,刷新页面
                        this.getList();
                        this.$message.success(res.msg);
                    }
                }
            },
            //删除按钮
            async deleteBtn(row){
                let confirm = await this.$myconfirm('确定要删除该数据吗?');
                if(confirm){
                    let res = await deleteApi({complaintId:row.complaintId});
                    if(res && res.code == 200){
                        //提示信息,刷新页面
                        this.getList();
                        this.$message.success(res.msg);
                    }
                }
            },
            //编辑按钮
            editBtn(row){
                //清空表单
                this.$resetForm('addForm',this.addModel);
                //设置弹窗属性
                this.addDialog.title = '新增投诉';
                this.addDialog.visible = true;
                //设置编辑状态
                this.addModel.editType = '0'; //0:新增，1：编辑
                //将该行数据赋值到数据域
                this.$objCoppy(row,this.addModel);
            },
            //弹窗确认
            onConfirm(){
                this.$refs.addForm.validate(async(valid) =>{
                    if(valid){
                        let res = null;
                        this.addModel.userId = getUserId();
                        if(this.addModel.editType == '0'){//新增
                            res = await addApi(this.addModel);  
                        }else{
                            res = await editApi(this.addModel);
                        }
                        if(res && res.code ==200){
                            //刷新页面，提示信息，关闭弹窗
                            this.getList();
                            this.$message.success(res.msg);
                            this.addDialog.visible = false;
                        }
                    }
                })
            },
            //弹窗关闭
            onClose(){
                this.addDialog.visible = false;
            },
            //当前页改变时触发
            currentChange(val){
                this.parm.currentPage = val;
                this.getList();
            },
            //页容量改变时触发
            sizeChange(val){
                this.parm.pageSize = val;
                this.getList();
            },
            // 新增按钮
            addBtn(){
                //清空表单
                this.$resetForm('addForm',this.addModel);
                //设置弹窗属性
                this.addDialog.title = '新增投诉';
                this.addDialog.visible = true;
                //设置编辑状态
                this.addModel.editType = '0'; //0:新增，1：编辑
            },
            //重置按钮
            resetBtn(){
                this.parm.complaintContent = '';
                this.parm.title = '',
                this.getList();
            },
            //查询按钮
            searchBtn(){
                this.getList();
            },
            //查询列表
            async getList(){
                let res = await getListApi(this.parm);
                console.log(res);
                if(res && res.code == 200){
                    this.tableList = res.data.records;
                    this.parm.total = res.data.total;
                }
            }
        },
    }
</script>

<style lang="scss" scoped>

</style>