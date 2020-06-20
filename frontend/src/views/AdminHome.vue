
<template>
  <div class="ui container" style="margin-top: 25px;">
    <div class="ui attached stackable menu">
      <div class="ui container">
        <a class="item">
          <i class="home icon"></i> Home
        </a>
        <a class="item" @click="manegerSccount">
          <i class="id badge outline icon"></i> Account
        </a>
        <a class="item">
          <i class="redhat icon"></i> Category
        </a>
        <div class="ui simple dropdown item">
          More
          <i class="dropdown icon"></i>
          <div class="menu">
            <a class="item" @click="logoutAccount()">
              <i class="edit icon"></i> Edit Profile
            </a>
            <a class="item">
              <i class="globe icon"></i> Choose Language
            </a>
            <a class="item">
              <i class="settings icon"></i> Account Settings
            </a>
          </div>
        </div>
        <div class="right item">
          <div class="ui action input">
            <input v-model="NameBrand" type="text" placeholder="Search Category..." />
            <button @click="serchNameBrand()" class="ui button">Search</button>
          </div>
        </div>
      </div>
    </div>
    <div style="margin-top: 75px;" class="ui buttons">
      <button @click="addDevice" class="ui button">Add Category</button>
    </div>
    <table class="ui celled padded table">
      <thead>
        <tr>
          <th>ID</th>
          <th>Type of device</th>
          <th>Brand</th>
          <th>Creat Date</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        <tr v-for=" category in categoris" :key="category.id">
          <td class="collapsing">{{category.id}}</td>
          <td class="single line">{{category.brandName}}</td>
          <td>
            <div
              class="ui yellow rating"
              data-rating="3"
              data-max-rating="3"
            >{{brandParent(category.idParent)}}</div>
          </td>
          <td class="right aligned">{{category.creatDate}}</td>
          <td>
            <div class="compact ui basic icon buttons">
              <button
                class="compact ui button"
                data-position="top right"
                data-variation="mini"
                @click="editCategory(category.id)"
              >
                <i class="edit outline icon"></i>
              </button>
              <button
                class="compact ui button"
                data-position="top right"
                data-variation="mini"
                @click="deleteCategory(category.id)"
              >
                <i class="trash alternate outline icon"></i>
              </button>
            </div>
          </td>
        </tr>
      </tbody>
      <tfoot>
        <tr>
          <th colspan="5">
            <div class="ui right floated pagination menu">
              <a class="icon item" @click="changePageIcon(0)">
                <i class="left chevron icon"></i>
              </a>
              <a class="item" v-for="n in arrayTotalPage" :key="n" @click="changePage(n)">{{n}}</a>
              <a class="icon item" @click="changePageIcon(1)">
                <i class="right chevron icon"></i>
              </a>
            </div>
          </th>
        </tr>
      </tfoot>
    </table>
    <div style="margin-top:75px" class="right item">
      <div style="float:right" class="ui action input">
        <input v-model="nameBrandSerch" type="text" placeholder="Search Brand ..." />
        <button @click="serchBrandDevice()" class="ui button">Search</button>
      </div>
    </div>
      <div class="ui buttons">
      <button @click="addBrandNew" class="ui button">Add Brand</button>
    </div>
    <table style="margin-bottom:100px" class="ui celled table">
      <thead>
        <tr>
          <th>ID</th>
          <th>Name Brand</th>
          <th>Creat Date</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="band in bandParents" :key="band.id">
          <td class="collapsing">{{band.id}}</td>
          <td data-label="Age">{{band.brandName}}</td>
          <td data-label="Job">{{band.creatDate}}</td>
          <td data-label="Job">
            <div class="compact ui basic icon buttons">
              <button
                class="compact ui button"
                data-position="top right"
                data-variation="mini"
                @click="editBrand(band.id)">
                <i class="edit outline icon"></i>
              </button>
              <button
                class="compact ui button"
                data-position="top right"
                data-variation="mini"
                @click="deleteBrand(band.id)">
                <i class="trash alternate outline icon"></i>
              </button>
            </div>
          </td>
        </tr>
      </tbody>
    </table>
    <div class="ui modal mini" id="modal-account-category-delete">
      <div class="ui header red">Delete</div>
      <div class="content">
        <p>Are you want delete?</p>
      </div>
      <div class="actions">
        <div class="ui compact cancel red button" @click="deleteCategoryHandle">Yes</div>
        <div class="ui compact cancel button">Cancel</div>
      </div>
    </div>
    <div class="ui large modal" id="modal-account-category-edit">
      <div v-if="idBrandWatch == 0" class="ui header red">Edit</div>
      <div v-else class="ui header red">Add Category</div>
      <div class="content">
        <form class="ui form">
          <div class="field">
            <label>Name Category</label>
            <input
              type="text"
              :class="[$v.device_edit.brandName.$error ? 'field error' : '']"
              v-model.trim="device_edit.brandName"
              placeholder="Name Category"
            />
            <template v-if="$v.device_edit.brandName.$error">
              <span class="ui red text" v-if="!$v.device_edit.brandName.required">Not must required</span>
              <span class="ui red text" v-if="!$v.device_edit.brandName.maxLength">Long??</span>
            </template>
          </div>
          <div v-show="idBrandWatch == 1 || idBrandWatch == 0" class="field">
            <label>Name Brand</label>
            <select
              class="ui dropdown"
              :class="[$v.device_edit.idParent.$error ? 'error' : '']"
              id="selected-category-brand-dropdown"
              v-model="selectedIdParent"
            >
              <option 
                v-for="bandParent in bandParents"
                :value="bandParent.id"
                :key="bandParent.id"
              >{{bandParent.brandName}}
              </option>
            </select>
            <template v-if="$v.device_edit.idParent.$error">
              <span class="ui red text" v-if="!$v.device_edit.idParent.required">Not must required</span>
            </template>
          </div>
          <button class="ui button" @click="saveCategory()" type="button">Save</button>
          <button class="ui button" @click="cancelSaveCategory()" type="button">Cancel</button>
        </form>
      </div>
    </div>
    <div class="ui large modal" id="modal-brand-edit">
      <div v-if="isBrand" class="ui header red"> Edit Brand</div>
      <div  v-else class="ui header red"> Add Brand</div>
      <div class="content">
        <form class="ui form">
          <div class="field">
            <label>Name Category</label>
            <input
              type="text"
              :class="[$v.device_edit.brandName.$error ? 'field error' : '']"
              v-model="brandDevice"
              placeholder="Name Category" />
            <template v-if="$v.brandDevice.$error">
              <span class="ui red text" v-if="!$v.brandDevice.required">Not must required</span>
            </template>
          </div>
          <button class="ui button" @click="saveBrand()" type="button">Save</button>
          <button class="ui button" @click="cancelSaveBrand()" type="button">Cancel</button>
        </form>
      </div>
    </div>
  </div>
</template>
<script>
import { callApi } from "../Api/callApi";
import { required, maxLength } from "vuelidate/lib/validators";
export default {
  name: "AdminHome",
  data() {
    return {
      categoris: [],
      device_edit: {
        id: "",
        brandName: "",
        idParent: "",
        delFlg: 0
      },
      bandParents: [],
      idBrandWatch: "",
      selectedIdParent: null,
      totalItem: 0,
      maxPageOnItem: 0,
      pages: 1,
      idParentBrand: "",
      NameBrand: "",
      brandDevice: "",
      idBrandDevice: "",
      countSave: false,
      isBrand: false,
      nameBrandSerch: ""
    };
  },

  created() {
    this.listDevice();
    this.getNameBrand();
  },

  mounted() {
    let self = this;
    $("#selected-category-brand-dropdown").dropdown({
      onChange(value, text, $choice) {
        self.device_edit.idParent = value;
      }
    });
  },

  computed: {
    nameParentBrand: function() {
      return this.brandParent(this.idParentBrand);
    },

    totalPage: function() {
      return parseInt(Math.ceil(this.totalItem / this.maxPageOnItem));
    },

    arrayTotalPage: function() {
      let arrayNumber = [];
      for (let i = 1; i <= this.totalPage; i++) {
        arrayNumber.push(i);
      }
      return arrayNumber;
    }
  },

  methods: {
    success(){
      $("body").toast({
          class: "success",
          message: `Success!`
        });
    },

    async listDevice() {
      let response = await callApi("GET", "account/admin/category/getall");
      if (response.status == 200) {
        this.categoris = response.data.results;
        this.totalItem = response.data.totalItem;
        this.maxPageOnItem = response.data.maxPageItem;
        this.pages = response.data.page;
      }
    },

    async getNameBrand() {
      let response = await callApi("GET", "account/admin/category/getparent");
      if (response.data) {
        this.bandParents = response.data;
      }
    },

    brandParent(id) {
      let name = this.bandParents.find(x => x.id == id);
      if (name) {
        return name.brandName;
      }
      return;
    },

    async deleteCategory(id) {
      let response = await callApi(
        "GET",
        "account/admin/category/getById",
        null,
        (id = { id })
      );
      if (response.status == 200) {
        this.device_edit = {
          id: response.data.id,
          brandName: response.data.brandName,
          idParent: response.data.idParent,
          delFlg: 1
        };
        this.isBrand=false;
        $("#modal-account-category-delete").modal("show");
      } else {
        this.listDevice();
      }
    },

    async addDevice() {
      this.idBrandWatch = 1;
      this.countSave = true;
      this.refestData();
      $("#selected-category-brand-dropdown").dropdown("set text", "");
      $("#modal-account-category-edit").modal("show");
    },

    async deleteCategoryHandle() {
      let response;
      if(this.isBrand) {
        response = await callApi(
        "POST",
        "account/admin/category/update",
        {
          id: this.idBrandDevice,
          brandName: this.brandDevice,
          idParent: 0,
          delFlg: 1
        });
      } else{
        response = await callApi(
        "POST",
        "account/admin/category/update",
        this.device_edit);
      }
      if (response.status == 200) {
        if(this.isBrand){
          this.getNameBrand()
        }else{
          this.listDevice();
        }   
        this.success();
        $("#modal-account-category-delete").modal("toggle");
      }
    },

    async editCategory(id) {
      this.idBrandWatch = 0;
      let response = await callApi(
        "GET",
        "account/admin/category/getById",
        null,
        (id = { id })
      );
      if (response.status == 200) {
        this.device_edit.brandName = response.data.brandName;
        this.device_edit.id = response.data.id;
        this.device_edit.delFlg = 0;
        this.device_edit.idParent = response.data.idParent;
        this.idParentBrand = response.data.idParent;
        $("#selected-category-brand-dropdown").dropdown(
          "set text",
          this.brandParent(response.data.idParent)
        );
        this.countSave = true;
        $("#modal-account-category-edit").modal("show");
      } else {
        this.listDevice();
      }
    },

    async saveCategory() {
      let self = this;
      let response;
      if (self.idBrandWatch == 2) {
        self.device_edit.idParent = 0;
      }
      self.$v.device_edit.$touch();
      if (self.$v.device_edit.$invalid) {
        return;
      }
      
      if(self.countSave){
        self.countSave = false;
        if (self.idBrandWatch == 0) {
        response = await callApi(
          "POST",
          "account/admin/category/update",
          this.device_edit
        );
      } else {
        response = await callApi("POST", "account/admin/category/create", {
          brandName: self.device_edit.brandName,
          idParent: self.device_edit.idParent,
          delFlg: 0
        });
      }
      
      if (response.status == 200) {
        self.listDevice();
        $("#modal-account-category-edit").modal("toggle");
        self.success();
      }
      }
      
    },

    cancelSaveCategory() {
      $("#modal-account-category-edit").modal("toggle");
    },

    async changePage(page) {
      if (page != this.pages) {
        let response = await callApi(
          "GET",
          "account/admin/category/getall",
          null,
          {
            page,
            nameBrand: this.NameBrand
          }
        );
        if (response.status == 200) {
          this.categoris = response.data.results;
          this.totalItem = response.data.totalItem;
          this.maxPageOnItem = response.data.maxPageItem;
          this.pages = response.data.page;
        }
      }
    },

    async changePageIcon(n) {
      let self = this;
      let page = self.pages;
      if (n == 0 && self.pages > 1) {
        page = self.pages - 1;
      }
      if (n == 1 && self.pages < self.totalPage) {
        page = self.pages + 1;
      }
      if (page != self.pages) {
        let response = await callApi(
          "GET",
          "account/admin/category/getall",
          null,
          {
            page,
            nameBrand: this.NameBrand
          }
        );
        if (response.status == 200) {
          this.categoris = response.data.results;
          this.totalItem = response.data.totalItem;
          this.maxPageOnItem = response.data.maxPageItem;
          this.pages = response.data.page;
        }
      }
    },
    addBrandNew(){
      this.isBrand = false;
      this.refestData();
      this.countSave = true;
      $('#modal-brand-edit').modal('show');
    },

    async editBrand(id){
      this.isBrand = true;
      $('#modal-brand-edit').modal('show');
      let response = await callApi(
      "GET",
      "account/admin/category/getById",
      null,
      (id = { id }));
      if(response.status == 200){
        this.brandDevice = response.data.brandName;
        this.idBrandDevice = response.data.id;
        this.countSave = true;
      }
    },
    async saveBrand(){
      let self = this;
      let response;
      self.$v.brandDevice.$touch();
      if (self.$v.brandDevice.$invalid) {
        return;
      }
      if(self.countSave){
        self.countSave = false;
        if(self.isBrand){
          response = await callApi("POST", "account/admin/category/update", {
          id: self.idBrandDevice,
          brandName: self.brandDevice,
          idParent: 0,
          delFlg: 0   });
        } else{
          response = await callApi("POST", "account/admin/category/create", {
          brandName: self.brandDevice,
          idParent: 0,
          delFlg: 0   });
        }
        
        if(response.status == 200) {
          this.success();
          self.getNameBrand();
        $('#modal-brand-edit').modal('toggle');}
      }
    },
    async deleteBrand(id){
        let response = await callApi(
        "GET",
        "account/admin/category/getById",
        null,
        (id = { id })
      );
      if(response.status == 200){
        this.isBrand = true;
        this.brandDevice = response.data.brandName;
        this.idBrandDevice = response.data.id;
        $("#modal-account-category-delete").modal("show");      
      }
    },

    cancelSaveBrand(){
      $('#modal-brand-edit').modal('toggle');
    },

    refestData() {
      this.$v.$reset();
      this.device_edit.brandName = "";
      this.device_edit.idParent = "";
      this.brandDevice="";
    },

    manegerSccount() {
      this.$router.push({ name: "AdminAccount" });
    },

    async serchBrandDevice(){
      let response = await callApi(
      "GET",
      "account/admin/category/getparent",
      null,
      { nameBrand: this.nameBrandSerch }
      );
      console.log(response, "aa")
      if(response.status == 200){
        this.bandParents = response.data;
      }
    },

    async serchNameBrand() {
      let response = await callApi(
        "GET",
        "account/admin/category/getall",
        null,
        { nameBrand: this.NameBrand }
      );
      if (response.status == 200) {
        this.categoris = response.data.results;
        this.totalItem = response.data.totalItem;
        this.maxPageOnItem = response.data.maxPageItem;
        this.pages = response.data.page;
      }
    },

    async logoutAccount(){
      
      let response = await callApi(
      "GET",
      "/logout");
      console.log(response,  "logout222221111")
      this.$router.push({ name: "Login" });
    }
  },

  validations() {
    return {
      device_edit: {
        brandName: {
          required,
          maxLength: maxLength(50)
        },
        idParent: {
          required
        }
      },
      brandDevice: {
        required
      }
    }
  }
};
</script>
