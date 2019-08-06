<template> 
  <div class="app-container">
    <el-card class="operate-container" shadow="never">
      <i class="el-icon-tickets"></i>
      <span>数据列表</span>
      <el-button size="mini" class="btn-add" @click="handleAdd()">添加</el-button>
    </el-card>
    <div class="table-container">
      <el-table ref="couponTable"
                :data="list"
                style="width: 100%;"
                @selection-change="handleSelectionChange"
                v-loading="listLoading" border>
        <el-table-column type="selection" width="60" align="center"></el-table-column>
        <el-table-column label="类目编号" width="100" align="center">
          <template slot-scope="scope">{{scope.row.categoryId}}</template>
        </el-table-column>
        <el-table-column label="类目名" align="center">
          <template slot-scope="scope">{{scope.row.categoryName}}</template>
        </el-table-column>
        <el-table-column label="类目类型" width="100" align="center">
          <template slot-scope="scope">{{scope.row.categoryType}}</template>
        </el-table-column>
        <el-table-column label="创建时间" width="180" align="center">
          <template slot-scope="scope">{{scope.row.createTime | formatDate}}</template>
        </el-table-column>

        <el-table-column label="修改时间" width="180" align="center">
          <template slot-scope="scope">{{scope.row.updateTime|formatDate}}</template>
        </el-table-column>

        <el-table-column label="操作" width="180" align="center">
          <template slot-scope="scope">
            <!--<el-button size="mini"-->
                       <!--type="text"-->
                       <!--@click="handleView(scope.$index, scope.row)">查看</el-button>-->
            <el-button size="mini"
                       type="primary"
                       @click="handleUpdate(scope.$index, scope.row)">
              编辑</el-button>
            <!--<el-button size="mini"-->
                       <!--type="text"-->
                       <!--@click="handleDelete(scope.$index, scope.row)">删除</el-button>-->
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="pagination-container">
      <el-pagination
        background
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        layout="total, sizes,prev, pager, next,jumper"
        :current-page.sync="listQuery.page"
        :page-size="listQuery.size"
        :page-sizes="[5,10,15]"
        :total="total">
      </el-pagination>
    </div>
  </div>
</template>
<script>
  import {fetchList,deleteCoupon} from '@/api/category';
  import {formatDate} from '@/utils/date';
  // const defaultListQuery = {
  //   page: 1,
  //   size: 10,
  //   name: null,
  //   type: null
  // };
  // const defaultTypeOptions=[
  //   {
  //     label: '全场赠券',
  //     value: 0
  //   },
  //   {
  //     label: '会员赠券',
  //     value: 1
  //   },
  //   {
  //     label: '购物赠券',
  //     value: 2
  //   },
  //   {
  //     label: '注册赠券',
  //     value: 3
  //   }
  // ];
  export default {
    name:'couponList',
    data() {
      return {
        // listQuery:Object.assign({},defaultListQuery),
        listQuery:Object.assign({}),
        // typeOptions:Object.assign({},defaultTypeOptions),
        typeOptions:Object.assign({}),
        list:null,
        total:null,
        listLoading:false,
        multipleSelection:[]
      }
    },
    created(){
      this.getList();
    },
    filters:{
      // formatType(type){
      //   for(let i=0;i<defaultTypeOptions.length;i++){
      //     if(type===defaultTypeOptions[i].value){
      //       return defaultTypeOptions[i].label;
      //     }
      //   }
      //   return '';
      // },
      // formatUseType(useType){
      //   if(useType===0){
      //     return '全场通用';
      //   }else if(useType===1){
      //     return '指定分类';
      //   }else{
      //     return '指定商品';
      //   }
      // },
      // formatPlatform(platform){
      //   if(platform===1){
      //     return '移动平台';
      //   }else if(platform===2){
      //     return 'PC平台';
      //   }else{
      //     return '全平台';
      //   }
      // },
      formatDate(time){
        if(time==null||time===''){
          return 'N/A';
        }
        let date = new Date(time);
        return formatDate(date, 'yyyy-MM-dd')
      },
      formatStatus(endTime){
        let now = new Date().getTime();
        if(endTime>now){
          return '未过期'
        }else{
          return '已过期';
        }
      }
    },
    methods:{

      handleSelectionChange(val){
        this.multipleSelection = val;
      },
      handleSizeChange(val) {
        this.listQuery.page = 1;
        this.listQuery.size = val;
        this.getList();
      },
      handleCurrentChange(val) {
        this.listQuery.page = val;
        this.getList();
      },
      handleAdd(){
        this.$router.push({path: '/sms/addCoupon'})
      },
      handleView(index, row) {
        this.$router.push({path: '/sms/couponHistory', query: {id: row.id}})
      },
      handleUpdate(index, row) {
        // alert(row.categoryId)
        this.$router.push({path: '/sms/updateCoupon', query: {categoryId: row.categoryId}})
        // this.$router.push({path: '/sms/updateCoupon'})
      },
      handleDelete(index, row) {
        this.$confirm('是否进行删除操作?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteCoupon(row.id).then(response=>{
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
            this.getList();
          });
        })
      },
      getList(){
        this.listLoading=true;
        fetchList(this.listQuery).then(response=>{
          this.listLoading = false;
          this.list = response.data.list;
          this.total = response.data.total;
        });
      }
    }
  }
</script>
<style scoped>
  .input-width {
    width: 203px;
  }
</style>


