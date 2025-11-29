<template>
    <el-main>
        <!-- 搜索框 -->
        <el-form :model="parm" ref="searchForm" label-width="80px" :inline="true" size="small">
            <el-form-item label="维修内容">
                <el-input v-model="parm.repairContent"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button icon="el-icon-search" @click="searchBtn">查询</el-button>
                <el-button icon="el-icon-close" style="color:#ff7670" @click="resetBtn">重置</el-button>
                <el-button type="primary" icon="el-icon-plus" @click="addBtn">新增</el-button>
            </el-form-item>
        </el-form>
        <!-- 表格 -->
        <el-table :height="tableHeight" :data="tableList" border stripe size="small">
        <el-table-column label="报修内容" prop="repairContent"></el-table-column>
        <el-table-column label="报修地址" prop="repairAddress"></el-table-column>
        <el-table-column label="联系电话" prop="phone"></el-table-column>
        <el-table-column label="处理状态" prop="status">
            <template slot-scope="scope">
            <el-tag v-if="scope.row.status == '1'" type="success" size="small"
                >已处理</el-tag
            >
            <el-tag v-if="scope.row.status == '0'" type="danger" size="small"
                >未处理</el-tag
            >
            </template>
        </el-table-column>
        <el-table-column label="操作" align="center">
            <template slot-scope="scope">
                <el-button type="primary" size="small" @click="editBtn(scope.row)" icon="el-icon-edit">编辑</el-button>
                <el-button type="danger" size="small" @click="deleteBtn(scope.row)" icon="el-icon-delete">删除</el-button>
                <!-- <el-button type="success" size="small" @click="doBtn(scope.row)" icon="el-icon-delete">处理</el-button> -->
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
        <!-- 弹窗 -->
        <sys-dialog
        :title="addDialog.title"
        :height="addDialog.height"
        :width="addDialog.width"
        :visible="addDialog.visible"
        @onClose="onClose"
        @onConfirm="onConfirm"
        >
            <template slot="content">
                <el-form :model="addModel" ref="addForm" :rules="rules" label-width="80px" :inline="false" size="small">
                    <el-form-item label="维修内容" prop="repairContent">
                        <el-input type="textarea" v-model="addModel.repairContent"></el-input>
                    </el-form-item>
                    <el-form-item label="联系电话" prop="phone">
                        <el-input v-model="addModel.phone"></el-input>
                    </el-form-item>
                    <el-form-item label="联系地址" prop="repairAddress">
                        <el-input v-model="addModel.repairAddress"></el-input>
                    </el-form-item>
                </el-form>
            </template>
        </sys-dialog>
    </el-main>
    
</template>
<script>
import{getListApi,addApi,editApi,deleteApi} from '@/api/userRepair'
import {getUserId} from '@/utils/auth'
import SysDialog from '@/components/system/SysDialog.vue'
    export default {
        components: { SysDialog },
        data() {
                return {
                //校验规则
                rules:{
                    phone:[{
                        trigger:'change',
                        required:true,
                        message:'请填写联系电话'
                    }],
                    repairAddress:[{
                        trigger:'change',
                        required:true,
                        message:'请填写联系地址'
                    }],
                    repairContent:[{
                        trigger:'change',
                        required:true,
                        message:'请填写维修内容'
                    }],
                },
                //新增或编辑弹窗参数
                addModel:{
                    editType: "",//编辑状态 0：新增，1：编辑
                    repairId: "",//维修id
                    userId: "",//业主id
                    phone: "", //联系电话
                    repairAddress: "",//维修地址
                    repairContent: "",//维修内容
                },
                //弹窗属性
                addDialog:{
                    title:'',
                    height:230,
                    width:580,
                    visible:false,
                },
                tableHeight:0,//设置表格高度
                tableList:[],//表格数据
                parm:{
                    pageSize:10, //页容量
                    currentPage:1, //当前页
                    repairContent:'', //维修内容
                    userId:'',  //业主id
                    total:0,//总条数
                },
            }
        },
        mounted() {
            this.$nextTick(()=>{
                this.tableHeight = window.innerHeight -178;
            })
        },
        created () {
          this.getList();  
        },
        methods: {
            //处理按钮
            async doBtn(row){
                if(row.status =='1'){
                    this.$message.warning('该维修已经处理，不要重复处理')
                    return;
                }
                let parm ={
                    repairId:row.repairId,
                    status:'1',
                };
                let confirm = await this.$myconfirm('确定要处理该条数据吗?');
                if(confirm){
                    let res = await editApi(parm);
                    if(res && res.code == 200){
                        //刷新页面，提示信息
                        this.getList();
                        this.$message.success(res.msg);
                    }
                }
            },
            //弹窗确认事件
            onConfirm(){
                this.$refs.addForm.validate(async (valid)=>{
                    if(valid){
                        let res = null;
                        this.addModel.userId = getUserId();
                        if(this.addModel.editType == '0'){ //新增
                            res = await addApi(this.addModel);
                        }else{
                            res = await editApi(this.addModel);
                        }
                        if(res && res.code == 200) {
                            //刷新页面，提示信息，关闭弹窗
                            this.getList();
                            this.$message.success(res.msg);
                            this.addDialog.visible = false;
                        }
                    }
                })
            },
            //弹窗关闭事件
            onClose(){
                this.addDialog.visible = false;
            },
            //删除按钮
            async deleteBtn(row){
                let confirm = await this.$myconfirm('确定要删除该条数据吗?');
                if(confirm){
                    let res = await deleteApi({repairId:row.repairId});
                    if(res && res.code == 200){
                        //刷新页面，提示信息
                        this.getList();
                        this.$message.success(res.msg);
                    }
                }
            },
            //编辑按钮
            editBtn(row){
                //清空表单数据
                this.$resetForm('addForm',this.addModel)
                //设置弹窗属性
                this.addDialog.visible = true;
                this.addDialog.title = '新增维修';
                //设置编辑状态
                this.addModel.editType = '0';
                //把该行数据赋值数据域
                this.$objCoppy(row,this.addModel);
            },
            //当前页改变时触发
            currentChange(val){
                this.parm.currentPage = val;
            },
            //页数改变时触发
            sizeChange(val){
                this.parm.pageSize = val;
            },
            //新增按钮
            addBtn(){
                //清空表单数据
                this.$resetForm('addForm',this.addModel)
                //设置弹窗属性
                this.addDialog.visible = true;
                this.addDialog.title = '新增维修';
                //设置编辑状态
                this.addModel.editType = '0';
            },
            //重置按钮
            resetBtn(){
                this.parm.repairContent = '';
                this.getList();
            },
            //搜索按钮
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