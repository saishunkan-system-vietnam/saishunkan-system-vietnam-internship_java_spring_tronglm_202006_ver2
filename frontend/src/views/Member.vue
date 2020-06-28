
<template>
  <div class="ui container" style="margin-top: 25px;">
    <button  @click="turnTeam">
        <i class="arrow left icon"></i>
    </button>
    <div class="text-title">Quản lý thành viên của đội</div>
    <table class="team-detail">
      <tr>
        <td>
          <table>
            <tr>
              <td>Tên Đội</td>
              <td>{{team.name_team}}</td>
            </tr>
            <tr>
              <td>Ngày thành lập</td>
              <td>{{team.founding_date}}</td>
            </tr>
            <tr>
              <td>Mô tả</td>
              <td>{{team.info}}</td>
            </tr>
          </table>
        </td>
        <td>
          <img id="image-member-logo" v-if="imgPreview" :src="imgPreview" />
        </td>
      </tr>
    </table>
    <div class="ui buttons">
      <button @click="addMember()" class="ui button">Thêm thành viên</button>
    </div>
    <table class="ui celled padded table">
      <thead>
        <tr>
          <th>id</th>
          <th>Tên Thành Viên</th>
          <th>Nick Name</th>
          <th>Vai trò </th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="item in members" :key="item.id">
          <td>{{item.id}}</td>
          <td class="single line">{{item.name_member}}</td>
          <td>
            <div class="ui yellow rating" data-rating="3" data-max-rating="3">{{item.nickname}}</div>
          </td>
          <td class="right aligned">{{labelMember(item.cap_flg)}}</td>
          <td>
            <div class="compact ui basic icon buttons">
              <button @click="editMember(item.id)" class="compact ui button" data-position="top right" data-variation="mini">
                <i class="edit outline icon"></i>
              </button>
              <button @click="deleteMember(item.id)" class="compact ui button" data-position="top right" data-variation="mini">
                <i class="trash alternate outline icon"></i>
              </button>
            </div>
          </td>
        </tr>
      </tbody>
    </table>
    <div class="ui modal" id="modal-member-edit">
      <div v-if="idMemberWatch" class="ui header red">Sửa thông tin</div>
      <div v-else class="ui header red">Thêm thành viên</div>
      <div class="content">
        <form class="ui form">
          <div class="field" :class="[$v.member.name_member.$error ? 'field error' : '']">
            <label>Tên thành viên</label>
            <input type="text" v-model.trim="member.name_member" placeholder="Tên thành viên" />
            <template v-if="$v.member.name_member.$error">
              <span class="ui red text" v-if="!$v.member.name_member.required">Không được bỏ trống!</span>
              <span class="ui red text"
                v-if="!$v.member.name_member.maxLength"
              >Tên thành viên quá dài!</span>
            </template>
          </div>
          <div class="field" :class="[$v.member.nickname.$error ? 'field error' : '']">
            <label>Nick name</label>
            <input type="text" v-model.trim="member.nickname" placeholder="Nick name" />
            <template v-if="$v.member.nickname.$error">
              <span class="ui red text" v-if="!$v.member.nickname.required">Không được bỏ trống!</span>
              <span class="ui red text" v-if="!$v.member.nickname.maxLength">Nick name quá dài!</span>
            </template>
          </div>
          <div class="field">
            <label>Vai trò</label>
            <div class="ui form">
              <div class="inline fields">
                <div class="field">
                  <div class="ui radio checkbox">
                    <input type="radio" v-model="member.cap_flg" value="0" name="frequency" />
                    <label>Đội viên</label>
                  </div>
                </div>
                <div class="field">
                  <div class="ui radio checkbox">
                    <input type="radio" v-model="member.cap_flg" value="1" name="frequency" />
                    <label>Đội trưởng</label>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <button class="ui button" @click="saveMember()" type="button">Save</button>
          <button class="ui button" @click="cancelSave()" type="button">Cancel</button>
        </form>
      </div>
    </div>
    <div class="ui modal mini" id="modal-member-delete">
      <div class="ui header red">Delete</div>
      <div class="content">
        <p>Are you want delete?</p>
      </div>
      <div class="actions">
        <div @click="deleteHandle()" class="ui compact cancel red button">Yes</div>
        <div @click="cancelDelete()" class="ui compact cancel button">Cancel</div>
      </div>
    </div>
  </div>
</template>

<script>
import { callApi } from "../Api/callApi";
import { required, maxLength, numeric, email } from "vuelidate/lib/validators";

export default {
  name: "Member",
  props: ["id_team"],
  data() {
    return {
      members: [],
      team: {},
      totalItem: 0,
      maxPageOnItem: 0,
      pages: 0,
      idMemberWatch: null,
      imgPreview: null,
      member: {
        id: "",
        name_member: "",
        nickname: "",
        del_flg: 0,
        cap_flg: 0
      }
    };
  },

  computed: {},

  created() {
    this.getListMembers();
    this.getInfoTeam();
  },
  mounted() {},

  computed: {},

  methods: {
    async getListMembers() {
      let response = await callApi("GET", "admin/getbyteam/member", null, {
        id_team: this.id_team
      });
      if (response.data.code != "0000") {
        return this.$router.push({ name: "Team" });
      }
      this.members = response.data.payload;
      this.totalItem = response.data.totalItem;
      this.maxPageOnItem = 5;
      this.pages = response.data.page;
    },
    labelMember(cap_flg) {
      if(cap_flg == 0){
        return "Đội viên";
      } return "Đội trưởng";
    },

    async getInfoTeam() {
      let response = await callApi("GET", "admin/team/getbyid", null, {
        id: this.id_team
      });
      if (response.data.code == "0000") {
        this.team = response.data.payload;
        if (response.data.payload.link_logo) {
          this.imgPreview =
            "http://localhost:8081" + response.data.payload.link_logo;
        } else {
          this.imgPreview = null;
        }
      }
    },
    addMember() {
      this.idMemberWatch = null;
      this.resfetData();
      this.member.cap_flg = 0;
      $("#modal-member-edit").modal("show");
    },

    async editMember(id){
      this.idMemberWatch = 1;
      let response = await callApi("GET", "admin/member/getbyid", null, {id: id});
      if(response.data.code == '0000'){
        this.member = {
          id: response.data.payload.id,
          name_member: response.data.payload.name_member,
          nickname: response.data.payload.nickname,
          cap_flg: response.data.payload.cap_flg
        }
        $("#modal-member-edit").modal("show");
      } else{
        this.getListMembers();
      }
    },

    async deleteMember(id){
      let response = await callApi("GET", "admin/member/getbyid", null, {id: id});
      if(response.data.code == '0000'){
        this.member.id = response.data.payload.id;
        $("#modal-member-delete").modal("show");
      } else{
        this.getListMembers();
      }
    },

    async deleteHandle(){
      let response = await callApi("POST", "admin/updateMember", {
          id: this.member.id,
          del_flg: 1
      });
      if(response.data.code == '0000'){
        this.getListMembers();
        this.sussefull(response.data.message);
        $("#modal-member-delete").modal("hide");
      }
    },
    cancelDelete(){
      $("#modal-member-delete").modal("hide");
    },
    async saveMember() {
      let response;
      this.$v.$touch();
      if (this.$v.$invalid) {
        return;
      }
      if (this.idMemberWatch == null) {
        response = await callApi("POST", "admin/createMember", {
          name_member: this.member.name_member,
          nickname: this.member.nickname,
          id_team: this.id_team,
          cap_flg: this.member.cap_flg
        });
        if (response.data.code == "0000") {
          this.getListMembers();
          this.sussefull(response.data.message);
          $("#modal-member-edit").modal("hide");
        } else {
          this.getListMembers();
        }
      } else{
        response = await callApi("POST", "admin/updateMember", {
          id: this.member.id,
          name_member: this.member.name_member,
          nickname: this.member.nickname,
          id_team: this.id_team,
          cap_flg: this.member.cap_flg
        });
         if (response.data.code == "0000") {
          this.getListMembers();
          this.sussefull(response.data.message);
          $("#modal-member-edit").modal("hide");
        } else {
          this.getListMembers();
        }
      }
    },

    resfetData(){
      this.$v.$reset();
      this.member = {
        id : "",
        name_member: "",
        nickname: "",
        id_team: "",
        cap_flg: 0
        }
    },

    sussefull(mess) {
      $("body").toast({
        class: "success",
        message: mess
      });
    },

    cancelSave() {
      $("#modal-member-edit").modal("hide");
    },

    turnTeam(){
       this.$router.push({ name: "Team" });
    }

  },

  validations() {
    return {
      member: {
        name_member: {
          required,
          maxLength: maxLength(45)
        },
        nickname: {
          required,
          maxLength: maxLength(15)
        }
      }
    };
  }
};
</script>
