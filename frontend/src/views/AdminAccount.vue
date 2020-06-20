
<template>
  <div class="ui container" style="margin-top: 25px;">
    <div class="ui attached stackable menu">
      <div class="ui container">
        <a class="item">
          <i class="home icon"></i> Home
        </a>
        <a class="item">
          <i class="id badge outline icon"></i> Account
        </a>
        <a class="item" @click="manegerCate">
          <i class="redhat icon"></i> Category
        </a>
        <div class="ui simple dropdown item">
          More
          <i class="dropdown icon"></i>
          <div class="menu">
            <a class="item">
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
            <input v-model="nameAccount" type="text" placeholder="Search..." />
            <button @click="serchNameAccount()" class="ui button">Search</button>
          </div>
        </div>
      </div>
    </div>
    <div style="margin-top: 75px;" class="ui buttons">
      <button @click="addAccount" class="ui button">Add Account User</button>
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
          <td class="single line">{{account.nameAccount}}</td>
          <td>
            <div class="ui yellow rating" data-rating="3" data-max-rating="3">{{account.mail}}</div>
          </td>
          <td class="right aligned">{{account.phoneNumber}}</td>
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
          <div class="field" :class="[$v.userAccount.nameAccount.$error ? 'field error' : '']">
            <label>Name Account</label>
            <input type="text" v-model.trim="userAccount.nameAccount" placeholder="Name Account" />
            <template v-if="$v.userAccount.nameAccount.$error">
              <p
                class="uk-text-danger"
                v-if="!$v.userAccount.nameAccount.required"
              >Not must required</p>
              <p class="uk-text-danger" v-if="!$v.userAccount.nameAccount.maxLength">Long??</p>
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
          <div class="field" :class="[$v.userAccount.phoneNumber.$error ? 'field error' : '']">
            <label>Phone Number</label>
            <input type="text" v-model.trim="userAccount.phoneNumber" placeholder="Phone Number" />
            <template v-if="$v.userAccount.phoneNumber.$error">
              <p
                class="uk-text-danger"
                v-if="!$v.userAccount.phoneNumber.required"
              >Not must required</p>
              <p class="uk-text-danger" v-if="!$v.userAccount.phoneNumber.maxLength">Long??</p>
              <p class="uk-text-danger" v-if="!$v.userAccount.phoneNumber.numeric">Must be a number</p>
            </template>
          </div>
          <div class="field" :class="[$v.userAccount.address.$error ? 'field error' : '']">
            <label>Address</label>
            <input type="text" v-model.trim="userAccount.address" placeholder="Address" />
            <template v-if="$v.userAccount.address.$error">
              <p class="uk-text-danger" v-if="!$v.userAccount.address.required">Not must required</p>
              <p class="uk-text-danger" v-if="!$v.userAccount.address.maxLength">Long??</p>
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
        nameAccount: "",
        mail: "",
        passWord: "",
        address: "",
        phoneNumber: "",
        roleName: "",
        delFlg: 0
      },
      categoris: [],
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
      let response = await callApi("GET", "account/admin/getall");
      if (response.status == 200) {
        this.accounts = response.data.results;
        this.totalItem = response.data.totalItem;
        this.maxPageOnItem = response.data.maxPageItem;
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
      if (response.status == 200) {
        this.userAccount = {
          id: response.data.id,
          nameAccount: response.data.nameAccount,
          mail: response.data.mail,
          address: response.data.address,
          phoneNumber: response.data.phoneNumber,
          delFlg: 1
        };
        $("#modal-account-account-delete").modal("show");
      } else {
        this.listAccount();
      }
    },

    async addAccount() {
      this.idAccountWatch = null;
      this.refestData();
      $("#modal-account-account-edit").modal("show");
    },

    async deleteAccountHandle() {
      let response = await callApi(
        "POST",
        "account/updateUser",
        this.userAccount
      );
      if (response.status == 200) {
        this.listAccount();
        this.success();
        $("#modal-account-account-delete").modal("hide");
      }
    },

    async editAccount(id) {
      this.idAccountWatch = 1;
      let response = await callApi("GET", "/account/getbyid", null, { id });
      if (response.status == 200) {
        this.userAccount = {
          id: response.data.id,
          nameAccount: response.data.nameAccount,
          mail: response.data.mail,
          address: response.data.address,
          phoneNumber: response.data.phoneNumber
        };
        $("#selected-account-role-dropdown").dropdown(
          "set selected",
          response.data.roleName
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
        self.userAccount.roleName = self.selecteRole;
        response = await callApi(
          "POST",
          "account/updateUser",
          self.userAccount
        );
      } else {
        self.userAccount = {
          nameAccount: self.userAccount.nameAccount,
          mail: self.userAccount.mail,
          passWord: "123456",
          address: self.userAccount.address,
          phoneNumber: self.userAccount.phoneNumber,
          roleName: self.selecteRole
        };
        response = await callApi("POST", "account/register", self.userAccount);
      }
      if (response.status == 200) {
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
        let response = await callApi("GET", "account/admin/getall", null, {
          page,
          nameAccount: this.nameAccount
        });
        if (response.status == 200) {
          this.accounts = response.data.results;
          this.totalItem = response.data.totalItem;
          this.maxPageOnItem = response.data.maxPageItem;
          this.pages = response.data.page;
        }
      }
    },
    refestData() {
      this.$v.$reset();
      this.userAccount = {
        nameAccount: "",
        mail: "",
        phoneNumber: "",
        address: "",
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
          "account/admin/getall",
          null,
          {
          page,
          nameAccount: this.nameAccount
        }
        );
        if (response.status == 200) {
          this.accounts = response.data.results;
          this.totalItem = response.data.totalItem;
          this.maxPageOnItem = response.data.maxPageItem;
          this.pages = response.data.page;
        }
      }
    },
    async serchNameAccount() {
      let response = await callApi(
        "GET",
        "account/admin/getall",
        null,
       {nameAccount: this.nameAccount}
      );
      if (response.status == 200) {
        this.accounts = response.data.results;
        this.totalItem = response.data.totalItem;
        this.maxPageOnItem = response.data.maxPageItem;
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
        nameAccount: {
          required,
          maxLength: maxLength(20)
        },
        mail: {
          email,
          required,
          maxLength: maxLength(30)
        },
        phoneNumber: {
          required,
          numeric,
          maxLength: maxLength(12)
        },
        address: {
          maxLength: maxLength(50)
        }
      }
    };
  }
};
</script>
