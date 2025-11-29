import http from "@/utils/http";

//分页查询
export async function getListApi(parm){
    return http.get('/api/feePower/getList',parm);
}
//新增
export async function addFeePowerApi(parm){
    return http.post('/api/feePower',parm);
}
//编辑
export async function editFeePowerApi(parm){
    return http.put('/api/feePower',parm);
}
//删除
export async function deleteFeePowerApi(parm){
    return http.delete('/api/feePower',parm);
}
//根据栋数id查询单元列表
export async function getUnitListByBuildIdApi(parm){
    return await http.get('/api/houseUnit/getUnitByBuildId',parm);
}
//栋数列表
export async function getBuildListApi(){
    return await http.get('/api/HouseBuilding/getBuildList');
}
//根据单元id获取房屋列表
export async function getHouseByUnitIdApi(parm){
    return await http.get('/api/houseList/getHouseByUnitId',parm);
}
//缴费
export async function payFeePowerApi(parm){
    return await http.post('/api/feePower/payFeePower',parm);
}