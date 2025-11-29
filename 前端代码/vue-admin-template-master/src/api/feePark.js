import http from "@/utils/http";

//分页查询
export async function getListApi(parm){
    return await http.get('/api/feePark/getList',parm);
}
//新增
export async function addFeeParkApi(parm){
    return await http.post('/api/feePark',parm);
}   
//编辑
export async function editFeeParkApi(parm){
    return await http.put('/api/feePark',parm);
}
//删除
export async function deleteFeeParkApi(parm){
    return await http.delete('/api/feePark',parm);
}
//缴费
export async function payFeeParkApi(parm){
    return await http.post('/api/feePark/payFeePark',parm);
}