import http from "@/utils/http"

//获取栋数列表
export async function getListApi(parm){
    return await http.get("/api/HouseBuilding/list",parm);
}
//添加栋数列表
export async function addApi(parm){
    return await http.post("/api/HouseBuilding",parm);
}
//修改栋数列表
export async function editApi(parm){
    return await http.put("/api/HouseBuilding",parm);
}
//删除栋数列表
export async function deleteApi(parm){
    return await http.delete("/api/HouseBuilding",parm);

}