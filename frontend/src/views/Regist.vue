<template>
  <div style="margin: 100px auto;">
    <div class="ui three column centered grid">
      <div class="column" style="box-shadow: silver 1px 1px 5px;padding: 50px 30px 50px 30px;border-radius: 10px;">
        <div class="ui middle aligned center aligned grid">
          <h1>Đăng ký tài khoản</h1>
        </div>
        <form style="margin-top:100px" class="ui form">
          <div class="field">
            <label>Tên tài khoản</label>
            <div class="ui input" :class="[$v.userAccount.name_account.$error ? 'error' : '']">
              <input type="text" v-model="userAccount.name_account" placeholder="Tên tài khoản" />
            </div>
            <template v-if="$v.userAccount.name_account.$error">
              <span class="ui red text" v-if="!$v.userAccount.name_account.required">
                Vui lòng nhập tên tài khoản!
                <br />
              </span>
              <span class="ui red text" v-if="!$v.userAccount.name_account.maxLength">Tên tài khoản quá dài</span>
            </template>
            <div v-if="!checkName">
              <span class="ui red text">Tên tài khoản đã tồn tại!</span>
            </div>
          </div>
          <div class="field">
            <label>Mật khẩu</label>
            <div class="ui input" :class="[$v.userAccount.password.$error ? 'error' : '']">
              <input type="password" v-model="userAccount.password" placeholder="Mật khẩu" />
            </div>
            <template v-if="$v.userAccount.password.$error">
              <span class="ui red text" v-if="!$v.userAccount.password.required">
                Vui lòng nhập mật khẩu!
                <br />
              </span>
              <span class="ui red text" v-if="!$v.userAccount.password.maxLength">
                Mật khẩu quá dài!
                <br />
              </span>
              <span class="ui red text" v-if="!$v.userAccount.password.minLength">
                Mậu khẩu phải lớn hơn 6 ký tự!
                <br />
              </span>
            </template>
          </div>
          <div class="field">
            <label>Nhập lại mật khẩu</label>
            <div class="ui input" :class="[checkPass ? '' : 'error']">
              <input
                type="password"
                @blur="changePass()"
                v-model="passwordCheck"
                placeholder="Nhập lại mật khẩu"
              />
            </div>
            <template v-if="!checkPass">
              <span class="ui red text">Không trùng mật khẩu!</span>
            </template>
          </div>
          <div class="field">
            <label>Mail</label>
            <div class="ui input" :class="[$v.userAccount.mail.$error ? 'error' : '']">
              <input  type="text" v-model.trim="userAccount.mail" placeholder="Mail" />
            </div>
            <template v-if="$v.userAccount.mail.$error">
              <span class="ui red text" v-if="!$v.userAccount.mail.required">
                Vui lòng nhập mail!
                <br />
              </span>
              <span class="ui red text" v-if="!$v.userAccount.mail.email">
                Mail không đúng định dạng!
                <br />
              </span>
            </template>
             <div v-if="!checkMail">
              <span class="ui red text">Mail đã tồn tại!</span>
            </div>
          </div>
          <div class="field">
            <label>Số điện thoại</label>
            <div class="ui input" :class="[$v.userAccount.phone_number.$error ? 'error' : '']">
              <input type="text" v-model.trim="userAccount.phone_number" placeholder="Số điện thoại" />
            </div>
            <template v-if="$v.userAccount.phone_number.$error">
              <span class="ui red text" v-if="!$v.userAccount.phone_number.required">
                Vui lòng nhập số điện thoại!
                <br />
              </span>
              <span class="ui red text" v-if="!$v.userAccount.phone_number.maxLength">
                Số quá dài!
                <br />
              </span>
              <span class="ui red text" v-if="!$v.userAccount.phone_number.numeric">
                Phải là số!
                <br />
              </span>
            </template>
             <div v-if="!checkPhone">
              <span class="ui red text">Số điện thoại đã tồn tại!</span>
            </div>
          </div>
          <div class="field"></div>
          <button class="ui button" @click="addAccount()" type="button">Đăng ký</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import { callApi } from "../Api/callApi";
import {
  required,
  maxLength,
  minLength,
  numeric,
  email
} from "vuelidate/lib/validators";

export default {
  name: "Regist",
  data() {
    return {
      userAccount: {
        name_account: "",
        password: "",
        mail: "",
        phone_number: ""
      },
      passwordCheck: "",
      checkName: true,
      checkMail: true,
      checkPhone: true,
      checkPass: true
    }
  },

  methods: {
    changePass() {
      if (this.passwordCheck == this.userAccount.password) {
        this.checkPass = true;
      } else {
        this.checkPass = false;
      }
    },
    addAccount: async function() {
      this.$v.$touch();
      if (this.$v.$invalid) {
        return;
      }
      this.changePass();
      if (!this.checkPass) {
        return;
      }
      let response = await callApi(
        "POST",
        "/register",
        this.userAccount
      );
      if (response.data.code == "0000") {
        $("body").toast({
          class: "success",
          message: `Sign Up Success! Please log in`
        });
        this.$router.push({ name: "Login" });
      } else {
        if(response.data.payload.name_account != null){
          this.checkName = false;
        }
        if(response.data.payload.mail != null){
          this.checkMail = false;
        }
        if(response.data.payload.phone_number != null){
          this.checkPhone = false;
        }
      }
    }
  },
  validations() {
    return {
      userAccount: {
        name_account: {
          required,
          maxLength: maxLength(20)
        },
        password: {
          required,
          maxLength: maxLength(20),
          minLength: minLength(6)
        },
        mail: {
          email,
          required,
          maxLength: maxLength(30)
        },
        phone_number: {
          numeric,
          required,
          maxLength: maxLength(20)
        }
      }
    };
  }
};
</script>