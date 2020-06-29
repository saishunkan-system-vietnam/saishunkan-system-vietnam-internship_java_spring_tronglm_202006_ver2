<template>
  <div>
    <div class="ui inverted segment">
      <div class="ui inverted secondary pointing menu container">
        <router-link
          class="item"
          :class="isActiveLink('AdminAccount') ? 'active' : ''"
          :to="{ name: 'AdminAccount'}"
        >
          <i class="home icon"></i> Trang chủ
        </router-link>
        <router-link
          class="item"
          :class="isActiveLink('Team') ? 'active' : (isActiveLink('Member') ? 'active': '')"
          :to="{ name: 'Team'}"
        >
          <i class="users icon"></i> Đội
        </router-link>
        <router-link
          class="item"
          :class="isActiveLink('Tournament') ? 'active' : (isActiveLink('TeamDuel') ? 'active': (isActiveLink('MatchTnm') ? 'active': ''))"
          :to="{ name: 'Tournament'}"
        >
          <i class="steam icon"></i> Giải đấu
        </router-link>

        <div class="item" @click="signout">
          <i class="sign out alternate icon"></i> Đăng xuất
        </div>
      </div>
    </div>
    <div class="ui modal mini" id="modal-signout-admin">
      <div class="ui header">sign out</div>
      <div class="content">
        <p>Bạn có muốn đăng xuất tài khoản không ?</p>
      </div>
      <div class="actions">
        <div @click="signOutHandle()" class="ui compact cancel red button">Yes</div>
        <div class="ui compact cancel button">Cancel</div>
      </div>
    </div>
    <router-view />
  </div>
</template>
<script>
import { callApi } from "../../Api/callApi";
export default {
  name: "LayoutAdmin",
  methods: {
    signout() {
      $("#modal-signout-admin").modal("show");  
    },
    async signOutHandle(){
      let response = await callApi("POST", "/logout");
      if(response.data.code == "0000"){
        this.$store.commit('increment', null);
        this.$router.push({ name: "Login" });          
      }
    },

    isActiveLink(routerName) {
      return this.$route.name == routerName;
    }
  }
};
</script>