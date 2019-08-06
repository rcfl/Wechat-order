<template>
  <div style="margin-top: 50px">
    <el-form :model="value" :rules="rules" ref="productInfoForm" label-width="120px" style="width: 600px" size="small">
      <el-form-item label="商品类目：" prop="categoryType">
        <el-cascader
          v-model="selectProductCateValue"
          :options="productCateOptions">
        </el-cascader>
      </el-form-item>
      <el-form-item label="商品名称：" prop="categoryName">
        <el-input v-model="value.productName"></el-input>
      </el-form-item>

      <el-form-item label="商品介绍：">
        <el-input
          :autoSize="true"
          v-model="value.productDescription"
          type="textarea"
          placeholder="请输入内容"></el-input>
      </el-form-item>

      <el-form-item label="商品售价：">
        <el-input v-model="value.productPrice"></el-input>
      </el-form-item>

      <el-form-item label="商品库存：">
        <el-input v-model="value.productStock"></el-input>
      </el-form-item>
      <el-form-item label="商品图片地址：">
        <el-input v-model="value.productIcon"></el-input>
      </el-form-item>

      <el-form-item style="text-align: center">
        <el-button type="primary" size="medium" @click="handleFinishCommit">提交</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import {fetchListWithChildren} from '@/api/productCate'
  import {fetchList} from '@/api/category'
  import {getProduct} from '@/api/product';

  export default {
    name: "ProductInfoDetail",
    props: {
      value: Object,
      isEdit: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        hasEditCreated:false,
        //选中商品分类的值
        selectProductCateValue: [],
        productCateOptions: [],
        brandOptions: [],
        rules: {
          categoryName: [
            // {required: true, message: '请输入商品名称', trigger: 'blur'},
            // {min: 2, max: 140, message: '长度在 2 到 140 个字符', trigger: 'blur'}
          ],
          // categoryType: [{required: true, message: '请选择商品分类', trigger: 'blur'}],
          // brandId: [{required: true, message: '请选择商品品牌', trigger: 'blur'}],
          description: [{required: true, message: '请输入商品介绍', trigger: 'blur'}],
          requiredProp: [{required: true, message: '该项为必填项', trigger: 'blur'}]
        }
      };
    },
    created() {
      this.getProductCateList();
      // this.getBrandList();
    },
    computed:{
      //商品的编号
      productId(){
        return this.value.productId;
      }
    },
    watch: {
      productId:function(newValue){
        if(!this.isEdit)return;
        if(this.hasEditCreated)return;
        if(newValue===undefined||newValue==null||newValue===0)return;
        this.handleEditCreated();
      },
      selectProductCateValue: function (newValue) {

        if (newValue != null) {
          // alert(newValue[0])
          this.value.categoryType = newValue[1];
          // alert(this.value.categoryType);alert(this.value.categoryName);
          //根据categoryType 找到 categoryName
          this.value.categoryName= this.getCateNameById(this.value.categoryType);

        } else {
          this.value.categoryType = null;
          this.value.categoryName=null;
        }
      }
    },
    methods: {
      //处理编辑逻辑


      handleEditCreated(){
        if(this.value.categoryType!=null){
          this.selectProductCateValue.push(this.value.categoryName);
          this.selectProductCateValue.push(this.value.categoryType);
        }
        this.hasEditCreated=true;
      },
      getProductCateList() {

        // fetchListWithChildren().then(response => {
        fetchList().then(response => {
          let data = response.data;
          let list = data.list;
          this.productCateOptions = [];
          for (let i = 0; i < list.length; i++) {
            this.productCateOptions.push({label: list[i].categoryName, value: list[i].categoryType});
            // this.productCateOptions.push({label: list[i].categoryName, value: list[i].productId, value: list[i].categoryType});
          }
        });
      },

      getCateNameById(categoryType){
        let categoryName=null;
        for(let i=0;i<this.productCateOptions.length;i++){

          if (this.productCateOptions[i].value == categoryType) {
              categoryName = this.productCateOptions[i].label;
              return categoryName;
          }

        }
        return name;
      },
      handleNext(formName){
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$emit('nextStep');
          } else {
            this.$message({
              message: '验证失败',
              type: 'error',
              duration:1000
            });
            return false;
          }
        });
      },
      handleFinishCommit(){
        // alert("flkds")
        this.$emit('finishCommit',this.isEdit);
      }

    }
  }
</script>

<style scoped>
</style>
