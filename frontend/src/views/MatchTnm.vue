<template>
  <div class="ui container" style="margin-top: 25px;">
    <button @click="turnTournament">
      <i class="arrow left icon"></i>
    </button>
    <div class="text-title">Quản lý trận đấu</div>
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
        <td>{{ tournament.info_tnm }}</td>
      </tr>
    </table>

    <div class="ui buttons">
      <button @click="addMatchInTnm()" class="ui button">Thêm trận đấu</button>
    </div>
    <table class="ui red table">
      <thead>
        <tr>
          <th>id</th>
          <th>Ngày thi đấu</th>
          <th>Đội A</th>
          <th>Đội B</th>
          <th>Trạng thái</th>
          <th>Điểm</th>
          <th>Chung cuộc</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="item in listMatch" :key="item.id">
          <td>{{ item.id }}</td>
          <td>{{ getStartTime(item.start_time) }}</td>
          <td>{{ getTeam(item).teamA.name }}</td>
          <td>{{ getTeam(item).teamB.name }}</td>
          <td v-html="getStatus(item.status_flg)"></td>
          <td>
            {{ getTeam(item).teamA.point }} - {{ getTeam(item).teamB.point }}
          </td>
          <td>{{ getTeam(item).result }}</td>
          <td>
            <button
              class="compact ui button"
              data-position="top right"
              data-variation="mini"
              @click="editMatchInTnm(item.id)"
            >
              <i class="edit outline icon"></i>
            </button>
          </td>
        </tr>
      </tbody>
      <tfoot>
        <div class="ui right floated pagination menu"></div>
      </tfoot>
    </table>
    <div class="ui modal large" id="modal-match-tournnament-add">
      <div class="ui header red">Xếp lịch thi đấu</div>
      <div class="content">
        <form class="ui form">
          <div
            class="field"
            :class="[$v.dateTimeLocal.$error ? 'field error' : '']"
          >
            <label>Thời gian bắt đầu</label>
            <input v-model="dateTimeLocal" type="datetime-local" />
            <template v-if="$v.dateTimeLocal.$error">
              <span class="ui red text" v-if="!$v.dateTimeLocal.required"
                >Không được bỏ trống!</span
              >
            </template>
          </div>
          <div class="field" :class="[$v.idTeamA.$error ? 'field error' : '']">
            <div class="ui sub header">Đội A</div>
            <div
              class="ui fluid selection dropdown"
              id="select-dropdown-team-a"
            >
              <input type="hidden" name="user" />
              <i class="dropdown icon"></i>
              <div class="default text">Chọn đội</div>
              <div class="menu">
                <div
                  v-for="item in teams"
                  :key="item.id"
                  class="item"
                  :data-value="item.id"
                >
                  {{ item.name_team }}
                </div>
              </div>
            </div>
            <template v-if="$v.idTeamA.$error">
              <span class="ui red text" v-if="!$v.idTeamA.required"
                >Không được bỏ trống!</span
              >
            </template>
          </div>
          <div class="field" :class="[$v.idTeamB.$error ? 'field error' : '']">
            <div class="ui sub header">Đội B</div>
            <div
              class="ui fluid selection dropdown"
              id="select-dropdown-team-b"
            >
              <input type="hidden" name="user" />
              <i class="dropdown icon"></i>
              <div class="default text">Chọn đội</div>
              <div class="menu">
                <div
                  v-for="item in teams"
                  :key="item.id"
                  class="item"
                  :data-value="item.id"
                >
                  {{ item.name_team }}
                </div>
              </div>
            </div>
            <template v-if="$v.idTeamB.$error">
              <span class="ui red text" v-if="!$v.idTeamB.required"
                >Không được bỏ trống!</span
              >
              <span class="ui red text" v-if="!$v.idTeamB.isVald"
                >Đội B phải khác đội A</span
              >
            </template>
          </div>
          <div
            class="field"
            :class="[$v.match.place_match.$error ? 'field error' : '']"
          >
            <label>Địa điểm thi đấu</label>
            <input v-model="match.place_match" type="text" />
            <template v-if="$v.match.place_match.$error">
              <span class="ui red text" v-if="!$v.match.place_match.required"
                >Không được bỏ trống!</span
              >
            </template>
          </div>
          <div style="margin-top:20px;" class="actions">
            <div class="ui primary button" @click="saveMatchInTnm">
              <i class="save outline icon"></i>Lưu
            </div>
            <div class="ui compact cancel button">
              <i class="stop circle outline icon"></i>Đóng
            </div>
          </div>
        </form>
      </div>
    </div>
    <div class="ui modal mini" id="modal-delete-team-tournament">
      <div class="ui header red">Delete</div>
      <div class="content">
        <p>Bạn có muốn xóa đội khỏi giải đấu ?</p>
      </div>
      <div class="actions">
        <div @click="deleteHandleTeamInTnm()" class="ui compact red button">
          Yes
        </div>
        <div @click="cancelDelete()" class="ui compact cancel button">
          Cancel
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { callApi } from "../Api/callApi";
import { required, maxLength, numeric, email } from "vuelidate/lib/validators";
import Calender from "../components/admin/Calender";
import moment from "moment";

export default {
  name: "MatchTnm",
  props: ["tnm_id"],
  data() {
    return {
      requestDataSaveTeam: {
        id: this.tnm_id,
        listTeam: []
      },
      teams: [],
      listTeam: [],
      members: [],
      tournament: {},
      idTeamA: "",
      idTeamB: "",
      dateTimeLocal: "",
      match: {
        id: null,
        str_start_time: "",
        place_match: "",
        listDetailMatch: []
      },
      listMatch: [],
      detail_match: {},
      idEditA: "",
      idEditB: "",
      idWatch: null    
    };
  },

  components: {
    Calender
  },

  created() {
    this.getListMatch();
    this.getListTeamInTnm();
    this.getInfoTournament();
  },

  mounted() {
    let self = this;
    $("#select-dropdown-team-a").dropdown({
      onChange(value, text, $choice) {
        self.idTeamA = value;
      }
    });
    $("#select-dropdown-team-b").dropdown({
      onChange(value, text, $choice) {
        self.idTeamB = value;
      }
    });
  },

  methods: {
    getTeam(item) {
      let teamA = { ...item.listDetailMatch[0] };
      let teamB = { ...item.listDetailMatch[1] };
      let infoTeamA = [...this.teams].find(x => x.id == teamA.id_team);
      let infoTeamB = [...this.teams].find(x => x.id == teamB.id_team);

      let pointTeamA = item.status_flg ? teamA.result.toString() : "";
      let pointTeamB = item.status_flg ? teamB.result.toString() : "";

      let result = "";
      if (item.status_flg) {
        result =
          pointTeamA > pointTeamB
            ? "W - L"
            : pointTeamA == pointTeamB
            ? "N - N"
            : "L - W";
      }

      let infoDetail = {
        teamA: {
          name: infoTeamA ? infoTeamA.name_team : "",
          point: pointTeamA
        },
        teamB: {
          name: infoTeamB ? infoTeamB.name_team : "",
          point: pointTeamB
        },
        result: result
      };
      return infoDetail;
    },
    getStatus(sta_flg) {
      if (sta_flg == 0) {
        return '<i style="height: 17px" class="ui green text play icon "></i> Chưa thi đấu';
      }
      return '<i style="height: 17px" class="ui red text pause icon "></i> Đã thi đấu';
    },

    getStartTime(time) {
      if (time) {
        return moment(time).format("YYYY-MM-DD HH:mm");
      }
      return;
    },

    addMatchInTnm() {
      this.idWatch = null;
      $("#modal-match-tournnament-add").modal("show");
    },

    async editMatchInTnm(id) {
      this.idWatch = 1;
      let response = await callApi("GET", "/match/get-match", null, {
        id: id,
        id_tournament: this.tnm_id
      });
      if (response.data.code == "0000") {
        this.detail_match = response.data.payload;
        $("#select-dropdown-team-a").dropdown(
          "set selected",
          this.detail_match.teams[0].id
        );
        $("#select-dropdown-team-b").dropdown(
          "set selected",
          this.detail_match.teams[1].id
        );
        this.match.id = this.detail_match.id;
        this.dateTimeLocal = this.detail_match.start_time;
        this.match.place_match = this.detail_match.place_match;
        this.idEditA = this.detail_match.teams[0].id;
        this.idEditB = this.detail_match.teams[1].id;
        $("#modal-match-tournnament-add").modal("show");
      }
    },

    async getListMatch() {
      let response = await callApi("GET", "/match/get-all", null, {
        id: this.tnm_id
      });
      if (response.data.code == "0000") {
        this.listMatch = response.data.payload;
      }
    },

    async deleteTeamInTnm(id) {},

    async deleteHandleTeamInTnm() {},

    async getListTeamInTnm() {
      let response = await callApi("GET", "team/gettournament", null, {
        id_tournament: this.tnm_id
      });
      if (!response.data.code) {
        return;
      }
      if (response.data.code == "0000") {
        this.teams = response.data.payload;
      }
    },

    checkExits(id) {},

    async saveMatchInTnm() {
      let response;
      this.$v.$touch();
      if (this.$v.$invalid) {
        return;
      }
      if(!this.idWatch){
        this.match.listDetailMatch = [
        {
          id: this.idEditA,
          id_team: this.idTeamA
        },
        {
          id: this.idEditB,
          id_team: this.idTeamB
        }
      ];
      this.match.str_start_time = moment(this.dateTimeLocal).format(
        "YYYY-MM-DD HH:mm"
      );
      response = await callApi("POST", "admin/match/create", this.match);
    } else{
      this.match.listDetailMatch = [
        {
          id_team: this.idTeamA,
        },
        {
          id_team: this.idTeamB
        }
      ];
      this.match.str_start_time = moment(this.dateTimeLocal).format(
        "YYYY-MM-DD HH:mm"
      );
      response = await callApi("POST", "admin/match/create", this.match);
    }
      
      if (!response.data.code) {
        return;
      }
      if (response.data.code == "0000") {
        this.getListMatch();
        $("#modal-match-tournnament-add").modal("hide");
      }
      // if(response.data.code == "0000"){
      //   this.getListTeamInTnm();
      //   $("#modal-team-tournnament-add").modal("hide");
      // }
    },

    async getListTeam() {
      // let response = await callApi("GET", "team/getbynotidtournament", null, {id_tournament: this.tnm_id});
      // if (!response.data.code) {
      //   return;
      // }
      // if (response.data.code == "0000") {
      //   this.listTeam = response.data.payload;
      // }
    },

    addTeamInTnm() {
      // this.requestDataSaveTeam.listTeam = [];
      // this.getListTeam();
      // $("#modal-team-tournnament-add").modal("show");
    },

    async getInfoTournament() {
      let response = await callApi("GET", "/tournament/getbyid", null, {
        id: this.tnm_id
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
      //$("#modal-member-edit").modal("hide");
    },

    turnTournament() {
      this.$router.push({ name: "Tournament" });
    }
  },
  validations() {
    let self = this;
    return {
      idTeamA: {
        required
      },
      idTeamB: {
        isVald: value => {
          if (value == self.idTeamA) {
            return false;
          }
          return true;
        },
        required
      },
      dateTimeLocal: {
        required
      },
      match: {
        place_match: {
          required
        }
      }
    };
  }
};
</script>
