import http from "@/utils/http";

//分页查询
export async function getListApi(parm){
    return await http.get('/api/houseList/list',parm);
}
//新增
export async function addHouseListApi(parm){
    return await http.post('/api/houseList',parm);
}
//修改
export async function editHouseListApi(parm){
    return await http.put('/api/houseList',parm);
}
//删除
export async function deleteHouseListApi(parm){
    return await http.delete('/api/houseList',parm);
}
//根据栋数id查询单元列表
export async function getUnitListByBuildIdApi(parm){
    return await http.get('/api/houseUnit/getUnitByBuildId',parm);
}
//栋数列表
export async function getBuildListApi(){
    return await http.get('/api/HouseBuilding/getBuildList');
}