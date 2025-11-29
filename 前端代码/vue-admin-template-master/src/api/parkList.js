import http from "@/utils/http";

//分页查询
export async function getListApi(parm){
    return await http.get('/api/parkList/list',parm);
}
//新增
export async function addApi(parm){
    return await http.post('/api/parkList',parm);
}
//编辑
export async function editApi(parm){
    return await http.put('/api/parkList',parm);
}
//删除
export async function deleteApi(parm){
    return await http.delete('/api/parkList',parm);
}
//查询已使用停车列表
export async function getParkListApi(){
    return await http.get('/api/parkList/getParkList')
}