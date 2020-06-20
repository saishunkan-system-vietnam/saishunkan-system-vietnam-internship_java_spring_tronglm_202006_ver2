
<template>
  <div>
    <div style="max-width: 600px; margin: 300px auto;">
      <div class="ui middle aligned center aligned grid">
        <div class="column">
          <h2 class="ui teal image header">
            <div class="content">Log-in to your account</div>
          </h2>
          <form class="ui large form">
            <div class="ui stacked segment">
              <div class="field" :class="[$v.login.nameAccount.$error ? 'field error' : '']">
                <div class="ui left icon input">
                  <i class="user icon"></i>
                  <input
                    type="text"
                    name="nameAccount"
                    v-model="login.nameAccount"
                    placeholder="Name Account"
                  />
                </div>
                <template v-if="$v.login.nameAccount.$error">
                  <span class="ui red text" v-if="!$v.login.nameAccount.required">Not must required</span>
                </template>
              </div>
              <div class="field" :class="[$v.login.passWord.$error ? 'field error' : '']">
                <div class="ui left icon input">
                  <i class="lock icon"></i>
                  <input
                    type="password"
                    v-model="login.passWord"
                    name="password"
                    placeholder="Password"
                  />
                </div>
                <template v-if="$v.login.passWord.$error">
                  <span class="ui red text" v-if="!$v.login.passWord.required">Not must required</span>
                </template>
              </div>
              <div @click="loginAccount" class="ui fluid large teal submit button">Login</div>
            </div>
            <div class="ui error message"></div>
          </form>

          <div class="ui message">
            New to us?
            <a href="#" @click="changeRouterRegist">Sign Up</a>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { callApi } from "../Api/callApi";
import { required } from "vuelidate/lib/validators";
export default {
  name: "Login",
  data() {
    return {
      login: {
        nameAccount: "",
        passWord: ""
      },
      checkLogin: false,
      infoLogin:{
        id: "",
        nameAccount: "",
        phoneNumber: "",
        roleName: ""
      }
    }
  },
  methods: {
    async loginAccount() {
      let self = this;
      this.$v.$touch();
      if (this.$v.$invalid) {
        return;
      }
      let frmData = new FormData();
      frmData.append("nameAccount", this.login.nameAccount);
      frmData.append("passWord", this.login.passWord);

      let response = await callApi("POST", "/login", frmData);
      if (response.status == 200) {
        self.infoLogin = {
          id: response.data.id,
          nameAccount: response.data.nameAccount,
          phoneNumber: response.data.phoneNumber,
          roleName: response.data.roleName
       } 
      self.$store.commit('increment', self.infoLogin);

      $("body").toast({
          class: "success",
          message: `Login Success! Hello Admin `});
        self.$router.push({ name: "AdminHome" });
        self.checkLogin = true;
    } 


      // if(response.status == 200){
      //   $("body").toast({
      //     class: "success",
      //     message: `Login Success! Hello You!`
      //   });
      //   let a=123;
      //   this.$router.push({ name: "AccountUser", params: { nameUser: a} });
      //   this.checkLogin = true;
      // }

      if(!this.checkLogin) {
        $('body')
         .toast({
          class: 'error',
          message: `Invalid account name or password incorrect! Please try again`});
      }
    },

    changeRouterRegist(){
      this.$router.push({ name: "Regist" });
    }
  },

  validations() {
    return {
      login: {
        nameAccount: {
          required
        },
        passWord: {
          required
        }
      }
    }
  }
};

</script>
