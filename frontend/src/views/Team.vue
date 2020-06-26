
<template>
  <div class="ui container" style="margin-top: 25px;">
    <div class="text-title">Quản lý đội</div>
    <div class="ui buttons">
      <button @click="addTeam()" class="ui button">Thêm đội</button>
    </div>
    <div style="float:right" class="ui action input">
      <input v-model.trim="name_serch" type="text" placeholder="Search ..." />
      <button  @click="serchTeam()" class="ui button">Search</button>
    </div>
    <table class="ui celled padded table">
      <thead>
        <tr>
          <th>ID</th>
          <th>Tên Team</th>
          <th>Ngày thành lập</th>
          <th>Thông tin thêm</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="item in teams" :key="item.id">
          <td>{{item.id}}</td>
          <td class="single line">{{item.name_team}}</td>
          <td>
            <div class="ui yellow rating" data-rating="3" data-max-rating="3">{{item.founding_date}}</div>
          </td>
          <td class="right aligned">{{item.info}}</td>
          <td>
            <div class="compact ui basic icon buttons">
              <button class="compact ui button" @click="editTeam(item.id)" data-position="top right" data-variation="mini">
                <i class="edit outline icon"></i>
              </button>
              <button @click="deleteTeam(item.id)" class="compact ui button" data-position="top right" data-variation="mini">
                <i class="trash alternate outline icon"></i>
              </button>
              <button
                class="compact ui button"
                data-position="top right"
                data-variation="mini"
                @click="manegerMemberRedirect(item.id)"
              >
                <i class="cog icon"></i>
              </button>
            </div>
          </td>
        </tr>
      </tbody>
    </table>
    <div class="ui modal" id="modal-team-edit">
      <div v-if="idTeamWatch" class="ui header red">Sửa thông tin</div>
      <div v-else class="ui header red">Thêm đội</div>
      <div class="content">
        <form class="ui form">
          <div class="field" :class="[$v.team.name_team.$error ? 'field error' : '']">
            <label>Tên đội</label>
            <input type="text" v-model.trim="team.name_team" placeholder="Tên đội" />
            <template v-if="$v.team.name_team.$error">
              <p class="uk-text-danger" v-if="!$v.team.name_team.required">Không được bỏ trống!</p>
              <p class="uk-text-danger" v-if="!$v.team.name_team.maxLength">Tên đội quá dài!</p>
            </template>
          </div>
          <div class="field">
            <label>Logo đội</label>
            <input type="file" ref="file" @change="showImage" />
            <img class="img-logo-all" v-if="imgPreview" :src="imgPreview" />
          </div>
          <div class="field" :class="[$v.team.founding_date.$error ? 'field error' : '']">
            <label>Ngày thành lập</label>
            <Calender type="date" v-model="team.founding_date"></Calender>
            <template v-if="$v.team.founding_date.$error">
              <p class="uk-text-danger" v-if="!$v.team.founding_date.required">Không được bỏ trống!</p>
            </template>
          </div>
          <div class="field">
            <label>Thông tin thêm</label>
            <textarea v-model.trim="team.info"></textarea>
          </div>
          <button class="ui button" @click="saveTeam()" type="button">Save</button>
          <button class="ui button" @click="cancelSave()" type="button">Cancel</button>
        </form>
      </div>
    </div>
    <div class="ui modal mini" id="modal-team-delete">
      <div class="ui header red">Delete</div>
      <div class="content">
        <p>Are you want delete?</p>
      </div>
      <div class="actions">
        <div @click="deleteHadleTeam()" class="ui compact cancel red button">Yes</div>
        <div @click="cancelDelete()" class="ui compact cancel button">Cancel</div>
      </div>
    </div>
  </div>
</template>

<script>
import { callApi } from "../Api/callApi";
import moment from "moment";
import { required, maxLength, numeric, email } from "vuelidate/lib/validators";
import Calender from "../components/admin/Calender";
export default {
  name: "Team",
  data() {
    return {
      teams: [],
      totalItem: 0,
      maxPageOnItem: 0,
      pages: 0,
      imgPreview: null,
      team: {
        id: "",
        name_team: "",
        del_flg: 0,
        founding_date: "",
        info: ""
      },
      idTeamWatch: "",
      name_serch: ""
    };
  },
  components: {
    Calender
  },

  created() {
    this.getListTeam();
  },
  mounted() {},

  computed: {},

  methods: {
    async getListTeam() {
      let response = await callApi("GET", "admin/team/getall");
      if (response.data.code == "0000") {
        this.teams = response.data.payload;
        this.totalItem = response.data.totalItem;
        this.maxPageOnItem = 5;
        this.pages = response.data.page;
      }
    },

    addTeam() {
      this.idTeamWatch = null;
      this.refestData();
      $("#modal-team-edit").modal("show");
    },
    async deleteTeam(id){
      let response = await callApi("GET", "admin/team/getbyid", null, {id: id});
      if(response.data.code == "0000"){
        this.team.id = response.data.payload.id
        $("#modal-team-delete").modal("show");
      } else{
        this.getListTeam();
      }
    },

    async deleteHadleTeam(){
       let frm = new FormData();
       frm.append("id", this.team.id);
       frm.append("del_flg", 1);
       let response = await callApi("POST", "admin/updateTeam", frm);
       if(response.data.code == "0000"){
         this.getListTeam();
         $("#modal-team-delete").modal("hide");
       } else {
         this.getListTeam();
       }
    },

    cancelDelete(){
      $("#modal-team-delete").modal("hide");
    },

    async editTeam(id){
      this.idTeamWatch = 1;
      let response = await callApi("GET", "admin/team/getbyid", null, {id: id});
      if(response.data.code == "0000"){
        this.team.id = response.data.payload.id;
        this.team.name_team = response.data.payload.name_team;
        this.team.founding_date = response.data.payload.founding_date;
        this.team.info = response.data.payload.info;
        if(response.data.payload.link_logo){
          this.imgPreview = "http://localhost:8081"+response.data.payload.link_logo;
        } else{
          this.imgPreview = null;
        }
        $("#modal-team-edit").modal("show");
      }
    },

    async saveTeam() {
      this.$v.$touch();
      if (this.$v.$invalid) {
        return;
      }
      let frm = new FormData();
      if(!this.idTeamWatch){
        frm.append("name_team", this.team.name_team);
        frm.append("founding_date", this.team.founding_date);
        frm.append("info", this.team.info);
        if(this.$refs.file.files[0]){
          frm.append("multipartFile", this.$refs.file.files[0]);
         }    
      let response = await callApi("POST", "admin/createTeam", frm);
      if(response.data.code == "0000"){
        this.getListTeam();
        $("#modal-team-edit").modal("hide");
      }
    } else{
      if(this.$refs.file.files[0]){
        frm.append("multipartFile", this.$refs.file.files[0]);
      }
      frm.append("id", this.team.id);
      frm.append("name_team", this.team.name_team);
      frm.append("founding_date", this.team.founding_date);
      frm.append("info", this.team.info);
      let response = await callApi("POST", "admin/updateTeam", frm);
      if(response.data.code == "0000"){
        this.getListTeam();
        $("#modal-team-edit").modal("hide");
        }
      }     
    },

    cancelSave() {
      $("#modal-team-edit").modal("hide");
    },

    manegerMemberRedirect(id) {
      this.$router.push({ name: "Member", params: { id_team: id } });
    },

    showImage(e) {
      let reader = new FileReader();
      let self = this;
      if(e.target.files[0]){
        reader.onload = function(e) {
        self.imgPreview = e.target.result;
       };
      }else{
         self.imgPreview = null;
      }
      if(e.target.files[0]){
        reader.readAsDataURL(e.target.files[0]);
      }
    },
    async serchTeam(){
      let response = await callApi("GET", "admin/team/getall", null, {name_team: this.name_serch});
      if (response.data.code == "0000") {
        this.teams = response.data.payload;
      }
    },

    refestData(){
      this.$refs.file.type = "text",
      this.$refs.file.type = "file",
      this.$v.$reset();
      this.team.id = "",
      this.team.name_team = "",
      this.team.founding_date = "",
      this.team.info = "",
      this.imgPreview = ""
    }
  },

  validations() {
    return {
      team: {
        name_team: {
          required,
          maxLength: maxLength(45)
        },
        founding_date: {
          required
        },
        info: {
          maxLength: maxLength(500)
        }
      }
    };
  }
};
</script>
