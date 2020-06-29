
<template>
  <div class="ui container" style="margin-top: 25px;">
    <div class="text-title">Quản lý giải đấu</div>
    <div class="ui buttons">
      <button @click="addTournament()" class="ui button">Thêm giải đấu</button>
    </div>
    <div style="float:right" class="ui action input">
      <input v-model.trim="name_serch" type="text" placeholder="Search ..." />
      <button @click="serchTournament()" class="ui button">Search</button>
    </div>
    <table class="ui celled padded table">
      <thead>
        <tr>
          <th>id</th>
          <th>Tên giải đấu</th>
          <th>Ngày bắt đầu</th>
          <th>Ngày kết thúc</th>
          <th>Thông tin giải</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="item in tournaments" :key="item.id">
          <td>{{item.id}}</td>
          <td class="single line">{{item.name_tnm}}</td>
          <td>
            <div class="ui yellow rating">{{item.start_date}}</div>
          </td>
          <td class="aligned">{{item.end_date}}</td>
          <td class="aligned" v-html="unencodeInfo_Tnm(item.info_tnm)"></td>
          <td>
            <div class="compact ui basic icon buttons">
              <button
                class="compact ui button"
                @click="editTournament(item.id)"
                data-position="top right"
                data-variation="mini"
              >
                <i class="edit outline icon"></i>
              </button>
              <button
                class="compact ui button"
                data-position="top right"
                data-variation="mini"
                @click="manegerTeamTnm(item.id)"
              >
                <i class="users cog icon"></i>
              </button>
              <button
                class="compact ui button"
                data-position="top right"
                data-variation="mini"
                @click="manegerMatchTnm(item.id)"
              >
                <i class="chess board icon"></i>
              </button>
              <button
                @click="deleteTeam(item.id)"
                class="compact ui button"
                data-position="top right"
                data-variation="mini"
              >
                <i class="trash alternate outline icon"></i>
              </button>
            </div>
          </td>
        </tr>
      </tbody>
    </table>
    <div class="ui modal" id="modal-tournament-edit">
      <div v-if="idTeamWatch" class="ui header red">Sửa thông tin</div>
      <div v-else class="ui header red">Thêm giải đấu</div>
      <div class="content">
        <form class="ui form">
          <div class="field" :class="[$v.tournament.name_tnm.$error ? 'field error' : '']">
            <label>Tên giải đấu</label>
            <input type="text" v-model.trim="tournament.name_tnm" placeholder="Tên giải đấu" />
            <template v-if="$v.tournament.name_tnm.$error">
              <span class="ui red text" v-if="!$v.tournament.name_tnm.required">Không được bỏ trống!</span>
              <span class="ui red text"
                v-if="!$v.tournament.name_tnm.maxLength"
              >Tên giải đấu quá dài!</span>
            </template>
          </div>
          <div class="ui grid container">
            <div
              class="four wide column field"
              :class="[$v.tournament.start_date.$error ? 'field error' : '']"
            >
              <label>Ngày bắt đầu</label>
              <Calender type="date" v-model="tournament.start_date"></Calender>
              <template v-if="$v.tournament.start_date.$error">
                <span class="ui red text"
                  v-if="!$v.tournament.start_date.required"
                >Không được bỏ trống!</span>
              </template>
            </div>
            <div
              class="four wide column field"
              :class="[$v.tournament.end_date.$error ? 'field error' : '']"
            >
              <label>Ngày kết thúc</label>
              <Calender type="date" v-model="tournament.end_date"></Calender>
              <template v-if="$v.tournament.end_date.$error">
                <span class="ui red text"
                  v-if="!$v.tournament.end_date.required"
                >Không được bỏ trống!</span>
              </template>
            </div>
          </div>
          <div class="ui grid container">
            <div class="four wide column field">
              <label>Điểm đội thắng</label>
              <input v-model="tournament.win_point" type="number" />
            </div>
            <div class="four wide column field">
              <label>Điểm đội thua</label>
              <input v-model="tournament.lose_point" type="number" />
            </div>
            <div class="four wide column field">
              <label>Điểm hòa nhau</label>
              <input v-model="tournament.equal_point" type="number" />
            </div>
          </div>
          <div class="field">
            <label>Thông tin giải</label>
            <ckeditor type="classic" v-model="tournament.info_tnm" ></ckeditor>
          </div>
          <button class="ui button" @click="saveTournament()" type="button">Save</button>
          <button class="ui button" @click="cancelSave()" type="button">Cancel</button>
        </form>
      </div>
    </div>
    <div class="ui modal mini" id="modal-tournament-delete">
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
  name: "Tournament",
  data() {
    return {
      imgPreview: null,
      idTeamWatch: "",
      name_serch: "",
      tournaments: [],
      tournament: {
        id: "",
        name_tnm: "",
        start_date: "",
        end_date: "",
        del_flg: "",
        info_tnm: "",
        win_point: 0,
        lose_point: 0,
        equal_point: 0
      }
    };
  },
  components: {
    Calender
  },

  created() {
    this.getListTournament();
  },
  mounted() {},

  computed: {},

  methods: {
    async getListTournament() {
      let response = await callApi("GET", "tournament/getall");
      if (response.data.code && response.data.code == "0000") {
        this.tournaments = response.data.payload;
      }
    },

    addTournament() {
      this.idTeamWatch = null;
      this.refestData();
      $("#modal-tournament-edit").modal("show");
    },

    async editTournament(id) {
      this.idTeamWatch = 1;
      let response = await callApi(
        "GET",
        "tournament/getbyid",
        null,
        (id = { id })
      );
      if(!response.data.code){
          return ;
      }
      if (response.data.code == "0000") {
        this.tournament = {
          id: response.data.payload.id,
          name_tnm: response.data.payload.name_tnm,
          start_date: response.data.payload.start_date,
          end_date: response.data.payload.end_date,
          del_flg: response.data.payload.del_flg,
          info_tnm: this.unencodeInfo_Tnm(response.data.payload.info_tnm),
          win_point: response.data.payload.win_point,
          lose_point: response.data.payload.lose_point,
          equal_point: response.data.payload.equal_point,
        };
        $("#modal-tournament-edit").modal("show");
      } else{
          this.getListTournament();
      }
    },

    encodeInfo_Tnm(info_tnm){
      return escape(info_tnm);
    },
    unencodeInfo_Tnm(info_tnmEncode){
      return unescape(info_tnmEncode);
    },

    async saveTournament() {
      console.log(this.tournament.info_tnm, "html")
      let response;
      this.$v.$touch();
      if (this.$v.$invalid) {
        return;
      }
      if(this.idTeamWatch){
        response = await callApi("POST", "/admin/tournament/update", {
        id: this.tournament.id,
        name_tnm: this.tournament.name_tnm,
        start_date: this.tournament.start_date,
        end_date: this.tournament.end_date,
        info_tnm: this.encodeInfo_Tnm(this.tournament.info_tnm),
        win_point: this.tournament.win_point,
        lose_point: this.tournament.lose_point,
        equal_point: this.tournament.equal_point
       });
      }else{
        response = await callApi("POST", "/admin/tournament/create", {
        name_tnm: this.tournament.name_tnm,
        start_date: this.tournament.start_date,
        end_date: this.tournament.end_date,
        info_tnm: this.encodeInfo_Tnm(this.tournament.info_tnm),
        win_point: this.tournament.win_point,
        lose_point: this.tournament.lose_point,
        equal_point: this.tournament.equal_point
       });
      }
      if(!response.data.code){
          return ;
      }
      if (response.data.code == "0000") {
        this.getListTournament();
        this.sussefull(response.data.message);
        $("#modal-tournament-edit").modal("hide");
      }
    },
    async deleteTeam(id) {
        let response = await callApi(
        "GET",
        "tournament/getbyid",
        null,
        (id = { id })
      );
      if(!response.data.code){
          return ;
      }
      if(response.data.code == "0000"){
         this.tournament.id = response.data.payload.id;
         $("#modal-tournament-delete").modal("show");
      } else{
          this.getListTournament();
      }

    },

    async deleteHadleTeam() {
        let response = await callApi("POST", "/admin/tournament/update", {
            id: this.tournament.id,
            del_flg: 1
        });
        if(!response.data.code){
          return ;
      }
      if(response.data.code == "0000"){
          this.getListTournament();
          this.sussefull(response.data.message);
          $("#modal-tournament-delete").modal("hide");
      }
    },

    cancelDelete() {
      $("#modal-tournament-delete").modal("toggle");
    },

    async editTeam(id) {},

    cancelSave() {
      $("#modal-tournament-edit").modal("hide");
    },

    async serchTournament() {
        let response = await callApi("GET", "tournament/getall", null, {name_tnm: this.name_serch});
        if(!response.data.code){
            return;
        }
        if (response.data.code == "0000") {
          this.tournaments = response.data.payload;
        }
    },
    sussefull(mess) {
      $("body").toast({
        class: "success",
        message: mess
      });
    },

    refestData() {
        this.$v.$reset();
        this.tournament = {
          id:  "",
          name_tnm: "",
          start_date: "",
          end_date: "",
          del_flg: "",
          info_tnm: "",
          win_point: 0,
          lose_point: 0,
          equal_point: 0
        };

    },

    manegerTeamTnm(id){
      this.$router.push({ name: "TeamDuel", params: { id_tnm: id } });
    },

    manegerMatchTnm(id){
      this.$router.push({ name: "MatchTnm", params: { tnm_id: id } });
    }

  },

  validations() {
    return {
      tournament: {
        name_tnm: {
          required,
          maxLength: maxLength(45)
        },
        start_date: {
          required
        },
        end_date: {
          required
        }
      }
    };
  }
};
</script>
