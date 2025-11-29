import http from "@/utils/http";

//分页查询
export async function getListApi(parm){
    return await http.get('/api/feeWater/getList',parm);
}
//新增
export async function addFeeWaterApi(parm){
    return await http.post('/api/feeWater',parm);
}   
//编辑
export async function editFeeWaterApi(parm){
    return await http.put('/api/feeWater',parm);
}
//删除
export async function deleteFeeWaterApi(parm){
    return await http.delete('/api/feeWater',parm);
}
//缴费
export async function payFeeWaterApi(parm){
    return await http.post('/api/feeWater/payFeeWater',parm);
}