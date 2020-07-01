<template>
  <div class="ui container" style="margin-top: 25px;">
    <button @click="turnTournament">
      <i class="arrow left icon"></i>
    </button>
    <div class="text-title">Quản lý đội trong giải đấu</div>
    <table class="team-detail">
      <tr>
        <td>
          <table>
            <tr>
              <td>Tên giải đấu</td>
              <td>{{ tournament.name_tnm }}</td>
            </tr>
            <tr>
              <td>Ngày bắt đầu</td>
              <td>{{ tournament.start_date }}</td>
            </tr>
            <tr>
              <td>Ngày kết thúc</td>
              <td>{{ tournament.end_date }}</td>
            </tr>
            <tr>
              <td>Điểm thắng</td>
              <td>{{ tournament.win_point }}</td>
            </tr>
            <tr>
              <td>Điểm thua</td>
              <td>{{ tournament.lose_point }}</td>
            </tr>
            <tr>
              <td>Điểm hòa</td>
              <td>{{ tournament.equal_point }}</td>
            </tr>
          </table>
        </td>
      </tr>
      <tr>
        <div class="ui segment">
          <td v-html="unencodeInfo_Tnm(tournament.info_tnm)"></td>
        </div>
      </tr>
    </table>

    <div class="ui buttons">
      <button @click="addTeamInTnm()" class="ui button">Thêm đội</button>
    </div>
    <table class="ui celled padded table">
      <thead>
        <tr>
          <th>id</th>
          <th>Tên Team</th>
          <th>Ngày thành lập</th>
          <th>Thông tin thêm</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="item in teams" :key="item.id">
          <td>{{ item.id }}</td>
          <td class="single line">{{ item.name_team }}</td>
          <td>
            <div
              class="ui yellow rating"
              data-rating="3"
              data-max-rating="3"
            >{{ item.founding_date }}</div>
          </td>
          <td class="aligned">{{ item.info }}</td>
          <td>
            <div class="compact ui basic icon buttons">
              <button
                @click="deleteTeamInTnm(item.id)"
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
      <tfoot>
        <div class="ui right floated pagination menu"></div>
      </tfoot>
    </table>
    <div class="ui modal large" id="modal-team-tournnament-add">
      <div class="ui header red">Danh sách các đội</div>
      <div class="content">
        <div class="ui four column grid">
          <div class="column" v-for="item in listTeam" :key="item.id" @click="selectTeam(item)">
            <div class="ui segment">
              <div style="min-height:100px">
                <img style="max-width:100%;" :src="'http://localhost:8081' + item.link_logo" />
              </div>
              <p class="pur-mt-10">
                <i v-if="checkExits(item.id)" class="ui green text check icon"></i>
                {{ item.name_team }}
              </p>
            </div>
          </div>
        </div>
        <div style="margin-top:20px;" class="actions">
          <div class="ui primary button" @click="saveTeamInTnm">
            <i class="save outline icon"></i>Lưu
          </div>
          <div class="ui compact cancel button">
            <i class="stop circle outline icon"></i>Đóng
          </div>
        </div>
      </div>
    </div>
    <div class="ui modal mini" id="modal-delete-team-tournament">
      <div class="ui header red">Delete</div>
      <div class="content">
        <p>Bạn có muốn xóa đội khỏi giải đấu ?</p>
      </div>
      <div class="actions">
        <div @click="deleteHandleTeamInTnm()" class="ui compact red button">Yes</div>
        <div @click="cancelDelete()" class="ui compact cancel button">Cancel</div>
      </div>
    </div>
    <div class="ui modal mini" id="modal-warnning-team-tournament">
      <div class="ui header yellow">WARNNING!!!</div>
      <div class="content">
        <p>Đội này đang có trong lịch thi đấu, không thế xóa!!</p>
      </div>
       <div class="actions">
        <div class="ui compact cancel button">Cancel</div>
      </div>
    </div>
  </div>
</template>

<script>
import { callApi } from "../Api/callApi";
import { required, maxLength, numeric, email } from "vuelidate/lib/validators";

export default {
  name: "TeamDuel",
  props: ["id_tnm"],
  data() {
    return {
      requestDataSaveTeam: {
        id: this.id_tnm,
        listTeam: []
      },
      teams: [],
      listTeam: [],
      members: [],
      tournament: {},
      idMemberWatch: null,
      id_team_update: 0,
      member: {
        id: "",
        name_member: "",
        nickname: "",
        del_flg: 0,
        cap_flg: 0
      }
    };
  },

  created() {
    this.getListTeamInTnm();
    this.getInfoTournament();
  },

  methods: {
    unencodeInfo_Tnm(info_tnmEncode) {
      return unescape(info_tnmEncode);
    },

    async deleteTeamInTnm(id) {
      this.id_team_update = id;
      let response = await callApi("GET", "team/getbyidtournament", null, {
        id: id
      });
      if (!response) {
        return;
      }
      if (response.data.code == "0008") {
        $("#modal-warnning-team-tournament").modal("show");
      }
      if (response.data.code == "0000") {
        $("#modal-delete-team-tournament").modal("show");
      }
      if (response.data.code == "0002") {
        this.getListTeamInTnm();
      }
    },

    async deleteHandleTeamInTnm() {
      let response = await callApi("POST", "admin/updateTeamInTnm", {
        id: this.id_team_update,
        id_tournament: 0
      });
      if (!response.data.code) {
        return;
      }
      if (response.data.code == "0000") {
        this.getListTeamInTnm();
        $("#modal-delete-team-tournament").modal("hide");
      }
    },

    async getListTeamInTnm() {
      let response = await callApi("GET", "team/gettournament", null, {
        id_tournament: this.id_tnm
      });
      if (!response) {
        return;
      }
      if (response.data.code == "0000") {
        this.teams = response.data.payload;
      }
    },

    selectTeam(item) {
      let idIndex = this.requestDataSaveTeam.listTeam.findIndex(
        x => x.id == item.id
      );
      if (idIndex == -1) {
        return this.requestDataSaveTeam.listTeam.push({ id: item.id });
      }
      this.requestDataSaveTeam.listTeam.splice(idIndex, 1);
    },

    checkExits(id) {
      let idIndex = this.requestDataSaveTeam.listTeam.findIndex(
        x => x.id == id
      );
      return idIndex >= 0 ? true : false;
    },

    async saveTeamInTnm() {
      if (this.requestDataSaveTeam.listTeam.length == 0) {
        $("#modal-team-tournnament-add").modal("hide");
        return;
      }
      let response = await callApi(
        "POST",
        "admin/tournament/addTeam",
        this.requestDataSaveTeam
      );
      if (!response.data.code) {
        return;
      }
      if (response.data.code == "0000") {
        this.getListTeamInTnm();
        $("#modal-team-tournnament-add").modal("hide");
      }
    },

    async getListTeam() {
      let response = await callApi("GET", "team/getbynotidtournament", null, {
        id_tournament: this.id_tnm
      });
      if (!response) {
        return;
      }
      if (response.data.code == "0000") {
        this.listTeam = response.data.payload;
      }
    },

    addTeamInTnm() {
      this.requestDataSaveTeam.listTeam = [];
      this.getListTeam();
      $("#modal-team-tournnament-add").modal("show");
    },

    async getInfoTournament() {
      let response = await callApi("GET", "/tournament/getbyid", null, {
        id: this.id_tnm
      });
      if (response.data.code == "0000") {
        this.tournament = response.data.payload;
      }
    },

    cancelDelete() {
      // $("#modal-member-delete").modal("hide");
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

    turnTournament() {
      this.$router.push({ name: "Tournament" });
    }
  }
};
</script>
