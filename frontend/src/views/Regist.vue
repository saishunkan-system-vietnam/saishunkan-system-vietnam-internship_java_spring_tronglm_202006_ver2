<template>
  <div style="margin: 100px 400px auto;">
    <div class="ui middle aligned center aligned grid">
      <h1>Regist Account</h1>
    </div>
    <form style="margin-top:100px" class="ui form">
      <div class="field">
        <label>Enter Name Account</label>
        <div class="ui input" :class="[$v.userAccount.passWord.$error ? 'error' : '']">
          <input type="text" v-model="userAccount.nameAccount" placeholder="Name Account" />
        </div>
        <template v-if="$v.userAccount.nameAccount.$error">
          <span class="ui red text" v-if="!$v.userAccount.nameAccount.required">
            Not must required
            <br />
          </span>
          <span class="ui red text" v-if="!$v.userAccount.nameAccount.maxLength">Long??</span>
        </template>
      </div>
      <div class="field">
        <label>Enter Pass Word</label>
        <div class="ui input" :class="[$v.userAccount.passWord.$error ? 'error' : '']">
          <input type="password" v-model="userAccount.passWord" placeholder="Pass Word" />
        </div>
        <template v-if="$v.userAccount.passWord.$error">
          <span class="ui red text" v-if="!$v.userAccount.passWord.required">
            Not must required
            <br />
          </span>
          <span class="ui red text" v-if="!$v.userAccount.passWord.maxLength">
            Long??
            <br />
          </span>
          <span class="ui red text" v-if="!$v.userAccount.phoneNumber.minLength">
            Must be than 6 char
            <br />
          </span>
        </template>
      </div>
      <div class="field">
        <label>Enter again Pass Word</label>
        <div class="ui input" :class="[checkPass ? '' : 'error']" >
        <input type="password" @blur="changePass()" v-model="passwordCheck" placeholder="Pass Word" />
        </div>
         <template v-if="!checkPass">
           <span class="ui red text"> The password does not match!</span>
         </template>
      </div>
      <div class="field">
        <label>Enter Mail</label>
        <div class="ui input" :class="[$v.userAccount.mail.$error ? 'error' : '']">
          <input type="text" v-model.trim="userAccount.mail" placeholder="Mail" />
        </div>
        <template v-if="$v.userAccount.mail.$error">
          <span class="ui red text" v-if="!$v.userAccount.mail.required">
            Not must required
            <br />
          </span>
          <span class="ui red text" v-if="!$v.userAccount.mail.maxLength">
            Long??
            <br />
          </span>
          <span class="ui red text" v-if="!$v.userAccount.mail.email">
            Must be email
            <br />
          </span>
        </template>
      </div>
      <div class="field">
        <label>Enter Phone Numer</label>
        <div class="ui input" :class="[$v.userAccount.phoneNumber.$error ? 'error' : '']">
          <input type="text" v-model.trim="userAccount.phoneNumber" placeholder="Phone Numer" />
        </div>
        <template v-if="$v.userAccount.phoneNumber.$error">
          <span class="ui red text" v-if="!$v.userAccount.phoneNumber.required">
            Not must required
            <br />
          </span>
          <span class="ui red text" v-if="!$v.userAccount.phoneNumber.maxLength">
            Long??
            <br />
          </span>
          <span class="ui red text" v-if="!$v.userAccount.phoneNumber.numeric">
            Must be number
            <br />
          </span>
        </template>
      </div>
      <div class="field">
        <label>Enter Address</label>
        <div class="ui input" :class="[$v.userAccount.address.$error ? 'error' : '']">
          <input type="text" v-model="userAccount.address" placeholder="Address" />
        </div>
        <template v-if="$v.userAccount.address.$error">
          <span class="ui red text" v-if="!$v.userAccount.address.maxLength">Long?</span>
        </template>
      </div>
      <div class="field"></div>
      <button class="ui button" @click="addAccount()" type="button">Submit</button>
    </form>
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
        nameAccount: "",
        passWord: "",
        mail: "",
        phoneNumber: "",
        address: "",
        roleName: "user"
      },
      passwordCheck: "",
      checkPass: true,
    };
  },
  methods: {
    changePass(){
      if(this.passwordCheck == this.userAccount.passWord){
         this.checkPass = true;
      }
      else {
        this.checkPass = false;
      }

    },
    addAccount: async function() {
      this.$v.$touch();
      if (this.$v.$invalid) {
        return;
      }
      this.changePass();
      if(!this.checkPass){
        return;
      }
      let response = await callApi(
        "POST",
        "/account/register",
        this.userAccount
      );
      if (response.status == 200) {
        $("body").toast({
          class: "success",
          message: `Sign Up Success! Please log in`
        });
        this.$router.push({ name: "Login" });
      } else {
        $("body").toast({
          class: "error",
          message: `Regist false! Please try again`
        });
      }
    }
  },
  validations() {
    return {
      userAccount: {
        nameAccount: {
          required,
          maxLength: maxLength(20)
        },
        passWord: {
          required,
          maxLength: maxLength(20),
          minLength: minLength(6)
        },
        mail: {
          email,
          required,
          maxLength: maxLength(30)
        },
        phoneNumber: {
          numeric,
          required,
          maxLength: maxLength(50)
        },
        address: {
          maxLength: maxLength(50)
        }
      }
    };
  }
};
</script>