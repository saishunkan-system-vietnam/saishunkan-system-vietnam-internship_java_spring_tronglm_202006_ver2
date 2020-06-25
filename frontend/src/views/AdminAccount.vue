
<template>
  <div class="ui container" style="margin-top: 25px;">
    <div class="text-title">
      Quản lý tài khoản
    </div>
    <div style="margin-top: 75px;" class="ui buttons">
      <button @click="addAccount" class="ui button">Thêm tài khoản</button>
    </div>
    <div style="float:right;margin-top: 75px;" class="ui action input">
      <input v-model="nameAccount" type="text" placeholder="Search ..." />
      <button @click="serchNameAccount()" class="ui button">Search</button>
    </div>
    <table class="ui celled padded table">
      <thead>
        <tr>
          <th>ID</th>
          <th>Name Account</th>
          <th>Mail</th>
          <th>Phone Number</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        <tr v-for=" account in accounts" :key="account.id">
          <td>{{account.id}}</td>
          <td class="single line">{{account.name_account}}</td>
          <td>
            <div class="ui yellow rating" data-rating="3" data-max-rating="3">{{account.mail}}</div>
          </td>
          <td class="right aligned">{{account.phone_number}}</td>
          <td>
            <div class="compact ui basic icon buttons">
              <button
                class="compact ui button"
                data-position="top right"
                data-variation="mini"
                @click="editAccount(account.id)"
              >
                <i class="edit outline icon"></i>
              </button>
              <button
                class="compact ui button"
                data-position="top right"
                data-variation="mini"
                @click="deleteAccount(account.id)"
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
    <div class="ui modal mini" id="modal-account-account-delete">
      <div class="ui header red">Delete</div>
      <div class="content">
        <p>Are you want delete?</p>
      </div>
      <div class="actions">
        <div class="ui compact cancel red button" @click="deleteAccountHandle">Yes</div>
        <div class="ui compact cancel button">Cancel</div>
      </div>
    </div>

    <div class="ui modal" id="modal-account-account-edit">
      <div v-if="idAccountWatch" class="ui header red">Edit Account</div>
      <div v-else class="ui header red">Add Account</div>
      <div class="content">
        <form class="ui form">
          <div class="field" :class="[$v.userAccount.name_account.$error ? 'field error' : '']">
            <label>Name Account</label>
            <input type="text" v-model.trim="userAccount.name_account" placeholder="Name Account" />
            <template v-if="$v.userAccount.name_account.$error">
              <p
                class="uk-text-danger"
                v-if="!$v.userAccount.name_account.required"
              >Not must required</p>
              <p class="uk-text-danger" v-if="!$v.userAccount.name_account.maxLength">Long??</p>
            </template>
          </div>
          <div class="field" :class="[$v.userAccount.mail.$error ? 'field error' : '']">
            <label>Mail</label>
            <input type="text" v-model.trim="userAccount.mail" placeholder="Mail" />
            <template v-if="$v.userAccount.mail.$error">
              <p class="uk-text-danger" v-if="!$v.userAccount.mail.required">Not must required</p>
              <p class="uk-text-danger" v-if="!$v.userAccount.mail.maxLength">Long??</p>
              <p class="uk-text-danger" v-if="!$v.userAccount.mail.email">Must be a mail</p>
            </template>
          </div>
          <div class="field" :class="[$v.userAccount.phone_number.$error ? 'field error' : '']">
            <label>Phone Number</label>
            <input type="text" v-model.trim="userAccount.phone_number" placeholder="Phone Number" />
            <template v-if="$v.userAccount.phone_number.$error">
              <p
                class="uk-text-danger"
                v-if="!$v.userAccount.phone_number.required"
              >Not must required</p>
              <p class="uk-text-danger" v-if="!$v.userAccount.phone_number.maxLength">Long??</p>
              <p class="uk-text-danger" v-if="!$v.userAccount.phone_number.numeric">Must be a number</p>
            </template>
          </div>
          <div class="field">
            <label>Role</label>
            <select class="ui dropdown" id="selected-account-role-dropdown" v-model="selecteRole">
              <option value="user">user</option>
              <option value="admin">admin</option>
            </select>
          </div>
          <button class="ui button" @click="saveAccount()" type="button">Save</button>
          <button class="ui button" @click="cancelSaveAccount()" type="button">Cancel</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import { callApi } from "../Api/callApi";
import { required, maxLength, numeric, email } from "vuelidate/lib/validators";
export default {
  name: "AdminAccount",
  data() {
    return {
      accounts: [],
      userAccount: {
        id: "",
        name_account: "",
        password: "",
        mail: "",
        role_name: "",
        phone_number: "",
        del_flg: "",
      },
      idAccountWatch: "",
      selecteRole: null,
      totalItem: 0,
      maxPageOnItem: 0,
      pages: 1,
      nameAccount: ""
    };
  },

  created() {
    this.listAccount();
  },
  mounted() {
    $("#selected-account-role-dropdown").dropdown();
  },

  computed: {
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
    async listAccount() {
      let response = await callApi("GET", "admin/getall");
      if (response.data.code == "0000") {
        this.accounts = response.data.payload;
        this.totalItem = response.data.totalItem;
        this.maxPageOnItem = 5;
        this.pages = response.data.page;
      }
    },

    async deleteAccount(id) {
      let response = await callApi(
        "GET",
        "account/getbyid",
        null,
        (id = { id })
      );
      if (response.data.code == "0000") {
        this.userAccount.id = response.data.payload.id;
        this.userAccount.del_flg = response.data.payload.del_flg;
        $("#modal-account-account-delete").modal("show");
      } else {
        this.listAccount();
      }
    },

    async deleteAccountHandle() {
      let response = await callApi(
        "POST",
        "/updateAccount",
        {
          id: this.userAccount.id,
          del_flg: 1
        }
      );
      if (response.data.code == "0000") {
        this.listAccount();
        this.success();
        $("#modal-account-account-delete").modal("toggle");
      }
    },

    addAccount() {
      this.idAccountWatch = null;
      this.refestData();
      $("#modal-account-account-edit").modal("show");
    },

    async editAccount(id) {
      this.idAccountWatch = 1;
      let response = await callApi("GET", "/account/getbyid", null, { id });
      if (response.data.code == "0000") {
        this.userAccount = {
          id: response.data.payload.id,
          name_account: response.data.payload.name_account,
          mail: response.data.payload.mail,
          phone_number: response.data.payload.phone_number
        };
        $("#selected-account-role-dropdown").dropdown(
          "set selected",
          response.data.payload.role_name
        );
        $("#modal-account-account-edit").modal("show");
      } else {
        this.listAccount();
      }
    },

    async saveAccount() {
      let self = this;
      let response;
      self.$v.$touch();
      if (self.$v.$invalid) {
        return;
      }
      if (self.idAccountWatch) {
       self.userAccount = {
          id: self.userAccount.id,
          name_account: self.userAccount.name_account,
          mail: self.userAccount.mail,
          phone_number: self.userAccount.phone_number,
          role_name: self.selecteRole
        };       
        response = await callApi(
          "POST",
          "/updateAccount",
          self.userAccount
        );
      } else {
        self.userAccount = {
          name_account: self.userAccount.name_account,
          mail: self.userAccount.mail,
          password: "123456",
          phone_number: self.userAccount.phone_number,
          role_name: self.selecteRole
        };
        response = await callApi("POST", "/register", self.userAccount);
      }
      if (response.data.code == "0000") {
        self.listAccount();
        self.success();
        $("#modal-account-account-edit").modal("toggle");
      }
    },

    cancelSaveAccount() {
      $("#modal-account-account-edit").modal("toggle");
    },
    async changePage(page) {
      if (page != this.pages) {
        let response = await callApi("GET", "admin/getall", null, {
          page,
          nameAccount: this.nameAccount
        });
        if (response.data.code == "0000") {
          this.accounts = response.data.payload;
          this.totalItem = response.data.totalItem;
          this.maxPageOnItem = 5;
          this.pages = response.data.page;
        }
      }
    },
    refestData() {
      this.$v.$reset();
      this.userAccount = {
        name_account: "",
        mail: "",
        phone_number: "",
        roleName: null,
        delFlg: 0
      };
      this.selecteRole = null;
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
          "admin/getall",
          null,
          {
          page,
          nameAccount: this.nameAccount
        }
        );
        if (response.data.code == "0000") {
          this.accounts = response.data.payload;
          this.totalItem = response.data.totalItem;
          this.maxPageOnItem = 5;
          this.pages = response.data.page;
        }
      }
    },
    async serchNameAccount() {
      let response = await callApi(
        "GET",
        "admin/getall",
        null,
       {nameAccount: this.nameAccount}
      );
      if (response.data.code == "0000") {
        this.accounts = response.data.payload;
        this.totalItem = response.data.totalItem;
        this.maxPageOnItem = 5
        this.pages = response.data.page;
      }
    },

    manegerCate() {
      this.$router.push({ name: "AdminHome" });
    }
  },

  validations() {
    return {
      userAccount: {
        name_account: {
          required,
          maxLength: maxLength(20)
        },
        mail: {
          email,
          required,
          maxLength: maxLength(50)
        },
        phone_number: {
          required,
          numeric,
          maxLength: maxLength(20)
        }
      }
    };
  }
};
</script>
