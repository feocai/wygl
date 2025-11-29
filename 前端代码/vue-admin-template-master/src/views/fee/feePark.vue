<template>
    <el-main>
       <!-- 搜索框 -->
       <el-form :model="parm" ref="researchForm" label-width="80px" :inline="true" size="small">
        <el-form-item label="姓名">
            <el-input v-model="parm.userName"></el-input>
        </el-form-item>
        <el-form-item label="车位名称">
            <el-input v-model="parm.parkName"></el-input>
        </el-form-item>
        <el-form-item>
            <el-button icon="el-icon-search" @click="searchBtn">查询</el-button>
            <el-button icon="el-icon-close" style="color:#ff7670" @click="resetBtn">重置</el-button>
            <el-button type="primary" icon="el-icon-plus" @click="addBtn">新增</el-button>
        </el-form-item>
       </el-form>
       <!-- 表格 -->
       <el-table :data="tableList" border stripe :height="tableHeight" size="small">
        <el-table-column label="业主姓名" prop="loginName"></el-table-column>
        <el-table-column label="电话" prop="phone"></el-table-column>
        <el-table-column label="车位名称" prop="parkName"></el-table-column>
        <el-table-column label="车位类型" prop="parkType">
            <template slot-scope="scope">
                <el-tag type="success" size="small" v-if="scope.row.parkType == '0'">地上</el-tag>
                <el-tag type="warning" size="small" v-if="scope.row.parkType == '1'">地下</el-tag>
            </template>
            
        </el-table-column>
        <el-table-column label="所属月份" prop="payParkMonth"></el-table-column>
        <el-table-column label="缴费金额" prop="payParkMoney"></el-table-column>
        <el-table-column label="缴费状态" prop="payParkStatus">
            <template slot-scope="scope">
                <el-tag type="success" size="small" v-if="scope.row.payParkStatus == '0'">未缴费</el-tag>
                <el-tag type="warning" size="small" v-if="scope.row.payParkStatus == '1'">已缴费</el-tag>
            </template>
        </el-table-column>
        <el-table-column label="操作" align="center" width="280">
            <template slot-scope="scope">
                <el-button type="primary" size="small" icon="el-icon-edit" @click="editBtn(scope.row)">编辑</el-button>
                <el-button type="danger" size="small" icon="el-icon-delete" @click="deleteBtn(scope.row)">删除</el-button>
                <el-button type="warning" size="small" v-if="scope.row.payParkStatus == '0'" @click="payParkBtn(scope.row)">缴费</el-button>
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
            <el-form :model="addModel" ref="addForm" :rules="rules" label-width="80px" :inline="true" size="small">
                <el-form-item label="车位名称" prop="parkId">
                    <el-select v-model="addModel.parkId" placeholder="请选择" clearable filterable>
                        <el-option v-for="item in parkList"
                            :key="item.parkId"
                            :label="item.parkName"
                            :value="item.parkId">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="所属月份" prop="payParkMonth">
                    <el-date-picker
                        v-model="addModel.payParkMonth"
                        type="month"
                        format="yyyy-MM"
                        value-format="yyyy-MM"
                        placeholder="选择月">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="缴费金额" prop="payParkMoney">
                    <el-input v-model="addModel.payParkMoney"></el-input>
                </el-form-item>
                <el-form-item label="缴费状态" prop="payParkStatus" style="margin-left:15px">
                     <el-radio-group v-model="addModel.payParkStatus">
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
import{getListApi,addFeeParkApi,editFeeParkApi,deleteFeeParkApi,payFeeParkApi} from '@/api/feePark';
import{getParkListApi} from '@/api/parkList';
import SysDialog from '@/components/system/SysDialog.vue'
    export default {
  components: { SysDialog },
        data() {
            return {
                parkList:[],//停车列表
                //校验规则
                rules:{
                parkId:[{
                    trigger:'change',
                    required:true,
                    message:'请选择车位名称',
                }],
                payParkMonth:[{
                    trigger:'change',
                    required:true,
                    message:'请选择所属月份',
                }],
                payParkMoney:[{
                    trigger:'change',
                    required:true,
                    message:'请填写缴费金额',
                }],
                payParkStatus:[{
                    trigger:'change',
                    required:true,
                    message:'请选择缴费状态',
                }],
                },
                //新增或编辑弹窗数据
                addModel:{
                    editType:'', //编辑状态 0：新增，1：编辑
                    parkFeeId:'',//主键
                    parkId:'',//车位id
                    userId:'',//用户id
                    payParkMonth:'',//缴费年月
                    payParkMoney:'',//缴费金额
                    payParkStatus:'',//缴费状态 0：未缴费，1：已缴费
                    payParkTime:'',//缴费时间
                    userName:'',//业主姓名
                    phone:'',//电话
                    parkName:'',//车位名称
                    parkType:'',//车位类型 0:地上，1：地下
                },
                //弹窗属性
                addDialog:{
                    title:'',
                    height:180,
                    width:620,
                    visible:false,
                },
                tableHeight:0,//表格高度
                tableList:[],//表格数据
                //查询参数
                parm:{
                    userName:'',//业主姓名
                    parkName:'',//车位名称
                    currentPage:1,//当前页
                    pageSize:10,//页容量
                    total:0, //总条数
                },
            }
        },
        mounted() {
            this.$nextTick(()=>{
                this.tableHeight = window.innerHeight - 178;
            })
        },
        created () {
        this.getList();
        this.getParkList();
        },
        methods: {
            //缴费按钮
            async payParkBtn(row){
                let payFeePark = await this.$myconfirm("确认要缴费吗？")
                console.log(row);
                if(payFeePark){
                    let parm = {
                        parkFeeId:row.parkFeeId,
                        payParkStatus:'1'
                    };
                    let res = await payFeeParkApi(parm)
                    if(res && res.code == 200){
                        //刷新列表，提示信息
                        this.getList();
                        this.$message.success(res.msg);
                    }
                }
            },  
            //弹窗确认事件
            onConfirm(){
                this.$refs.addForm.validate(async(valid) =>{
                    if(valid){
                        let res = null;
                        if(this.addModel.editType == '0'){//新增
                            res = await addFeeParkApi(this.addModel);
                        }else{//编辑
                            res = await editFeeParkApi(this.addModel);
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
            onClose(){
                this.addDialog.visible = false;
            },
            //页数改变时触发
            currentChange(val){
                this.parm.currentPage = val;
                this.getList();
            },
            //页容量改变时触发
            sizeChange(val){
                this.parm.pageSize = val;
                this.getList();
            },
            //删除按钮
            async deleteBtn(row){
                let confirm = await this.$myconfirm('确定要删除吗？')
                if(confirm){
                    let res = await deleteFeeParkApi({parkId:row.parkFeeId});
                    if(res && res.code == 200){
                        //提示信息，刷新页面
                        this.getList();
                        this.$message.success(res.msg);
                    }
                }
            },
            //编辑按钮
            editBtn(row){
                //清空表单数据
                this.$resetForm('addForm',this.addModel);
                //设置弹窗属性
                this.addDialog.title = '编辑停车费';
                this.addDialog.visible = true;
                //设置编辑状态
                this.addModel.editType = '1';//编辑状态 0：新增，1：编辑
                //将该行数据赋值到数据域
                this.$objCoppy(row,this.addModel);
            },
            //添加按钮
            addBtn(){
                //清空表单数据
                this.$resetForm('addForm',this.addModel)
                //设置弹窗属性
                this.addDialog.visible = true;
                this.addDialog.title = '新增停车费'
                //设置编辑状态
                this.addModel.editType = '0';//编辑状态 0：新增，1：编辑
            },
            //重置按钮
            resetBtn(){
                this.parm.userName = '';
                this.parm.parkName = '';
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
            },
            //查询已使用停车列表
            async getParkList(){
                let res = await getParkListApi();
                //console.log(res);
                if(res && res.code == 200){
                    this.parkList = res.data;
                }
            }  
        },
    }
</script>

<style lang="scss" scoped>

</style>