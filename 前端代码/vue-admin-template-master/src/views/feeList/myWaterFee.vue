<template>
    <el-main>
        <!-- 表格 -->
        <el-table :data="tableList" border stripe :height="tableHeight" size="small">
            <el-table-column prop="payWaterMonth" label="缴费月份"></el-table-column>
            <el-table-column prop="payWaterMoney" label="缴费金额"></el-table-column>
            <el-table-column prop="payWaterStatus" label="缴费状态">
                <template slot-scope="scope">
                    <el-tag type="danger" size="small" v-if="scope.row.payWaterStatus == '0'">未交费</el-tag>
                    <el-tag type="success" size="small" v-if="scope.row.payWaterStatus == '1'">已交费</el-tag>
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
    </el-main>
</template>
<script>
import { getUserId } from "@/utils/auth";
import {getMyWaterFeeApi} from '@/api/myFee'
    export default {
        data() {
            return {
                tableHeight:0,
                //表格数据
                tableList:[],
                //查询参数
                parm:{
                    currentPage:1,
                    pageSize:10,
                    userId:getUserId(),
                    total:0,
                }
            }
        },
        mounted() {
            this.$nextTick(()=>{
                this.tableHeight = window.innerHeight -178
            })
        },
        created () {
          this.getList();  
        },
        methods: {
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
            async getList(){
                let res = await getMyWaterFeeApi(this.parm);
                //console.log(res);
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