import http from "@/utils/http";

//查询
export async function getListApi(param){
    return await http.get('/api/houseUnit/list',param);
}
//添加
export async function addApi(param){
    return await http.post('/api/houseUnit',param);
}
//修改
export async function editApi(param){
    return await http.put('/api/houseUnit',param);
}
//删除
export async function deleteApi(param){
    return await http.delete('/api/houseUnit',param);
}
//获取栋数列表
export async function getBuildListApi(){
    return await http.get('/api/HouseBuilding/getBuildList');
}