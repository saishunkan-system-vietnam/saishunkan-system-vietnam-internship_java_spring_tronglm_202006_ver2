
<template>
  <div>
    <div style="max-width: 600px; margin: 300px auto;">
      <div class="ui middle aligned center aligned grid">
        <div class="column">
          <h2 class="ui teal image header">
            <div class="content">Đăng nhập</div>
          </h2>
          <form class="ui large form">
            <div class="ui stacked segment">
              <div class="field" :class="[$v.login.name_account.$error ? 'field error' : '']">
                <div class="ui left icon input">
                  <i class="user icon"></i>
                  <input
                    type="text"
                    name="nameAccount"
                    v-model="login.name_account"
                    placeholder="Tên tài khoản"
                  />
                </div>
                <template v-if="$v.login.name_account.$error">
                  <span class="ui red text" v-if="!$v.login.name_account.required">Không được bỏ trống!</span>
                </template>
              </div>
              <div class="field" :class="[$v.login.password.$error ? 'field error' : '']">
                <div class="ui left icon input">
                  <i class="lock icon"></i>
                  <input
                    type="password"
                    v-model="login.password"
                    name="password"
                    placeholder="Mật khẩu"
                  />
                </div>
                <template v-if="$v.login.password.$error">
                  <span class="ui red text" v-if="!$v.login.password.required">Không được bỏ trống</span>
                </template>
              </div>
              <div @click="loginAccount" class="ui fluid large teal submit button">Login</div>
            </div>
            <div class="ui error message"></div>
          </form>

          <div class="ui message">
            Bạn có tài khoản chưa?
            <a href="#" @click="changeRouterRegist">Đăng ký</a>
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
        name_account: "",
        password: ""
      },
      infoLogin:{}
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
      frmData.append("name_account", this.login.name_account);
      frmData.append("password", this.login.password);
      let response = await callApi("POST", "/login", frmData);
      if (response.data.code == "0000") {
        self.infoLogin = response.data.payload;
        self.$store.commit('increment', self.infoLogin);
      $("body").toast({
          class: "success",
          message: response.data.message
      });
        if(response.data.payload.role_name == "admin"){
           self.$router.push({ path: "/admin" });
        } else{
          self.$router.push({ name: "AccountUser" });
        }
      }
      if(response.data.code != "0000"){
        $("body").toast({
          class: "error",
          message: response.data.message
      });
      }
    } ,
    changeRouterRegist(){
      this.$router.push({ name: "Regist" });
    }
   },

  validations() {
    return {
      login: {
        name_account: {
          required
        },
        password: {
          required
        }
      }
    }
  }
};

</script>
