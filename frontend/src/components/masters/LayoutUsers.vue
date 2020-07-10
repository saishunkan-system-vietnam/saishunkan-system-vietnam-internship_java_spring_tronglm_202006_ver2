<template>
  <div>
    <div class="ui inverted segment">
      <div class="ui inverted secondary pointing menu container">
        <div class="item">
          <div class="ui simple dropdown item custom-menu" id="select-menu-tournament-dropdown">
            Giải đấu
            <i class="Giải đấu"></i>
            <div class="menu">
              <div
                class="item"
                v-for="item in listTournament"
                :key="item.id"
                @click="getInfoTournament(item.id)"
              >{{item.name_tnm}}</div>
            </div>
          </div>
        </div>
        <div class="item" @click="signout">
          <i class="sign out alternate icon"></i> Đăng xuất
        </div>
      </div>
    </div>
    <div class="ui container">
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
            <td v-if="tournament.info_tnm" v-html="unencodeInfo_Tnm(tournament.info_tnm)"></td>
            <td v-else></td>
          </div>
        </tr>
      </table>

      <div class="text-title">Bảng xếp hạng</div>
      <div class="ui grid">
        <div class="row">
          <div style="max-height:407px;overflow: scroll" class="eight wide column">
            <h2 style="text-align:center">Đội</h2>
            <table class="ui inverted table">
              <thead>
                <tr>
                  <th>Xếp Hạng</th>
                  <th>Tên Đội</th>
                  <th>Tổng Điểm</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(item, index) in listTeamRank" :key="item.id">
                  <td>{{index + 1}}</td>
                  <td>{{item.name_team}}</td>
                  <td>{{item.points}}</td>
                </tr>
              </tbody>
              <tfoot>
                <tr>
                  <th></th>
                  <th></th>
                  <th></th>
                </tr>
              </tfoot>
            </table>
          </div>
          <div style="max-height:407px;overflow: scroll" class="right floated four wide column">
            <h2>Cầu thủ</h2>
            <table class="ui very basic collapsing celled table">
              <thead>
                <tr>
                  <th>Xếp hạng</th>
                  <th>Cầu thủ</th>
                  <th>Ghi được</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(item, index) in listMemberInTNM" :key="item.id">
                  <td>{{index+1}}</td>
                  <td>{{item.nickname}}</td>
                  <td>{{item.scored}}</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>

      <div class="text-title">Danh sách trận đấu</div>
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
            <td>{{ getTeam(item).teamA.point }} - {{ getTeam(item).teamB.point }}</td>
            <td>{{ getTeam(item).result }}</td>
            <td>
              <div class="compact ui basic icon buttons">
                <button
                  class="compact ui button"
                  data-position="top right"
                  data-variation="mini"
                  @click="viewMatch(item.id)"
                >
                  <i class="eye icon"></i>
                </button>
              </div>
            </td>
          </tr>
        </tbody>
        <tfoot>
          <tr>
            <th></th>
            <th></th>
            <th></th>
            <th></th>
            <th></th>
            <th></th>
            <th></th>
            <th></th>
          </tr>
        </tfoot>
      </table>

      <div class="ui modal mini" id="modal-signout-user">
        <div class="ui header">sign out</div>
        <div class="content">
          <p>Bạn có muốn đăng xuất tài khoản không ?</p>
        </div>
        <div class="actions">
          <div @click="signOutHandle()" class="ui compact cancel red button">Yes</div>
          <div class="ui compact cancel button">Cancel</div>
        </div>
      </div>
    </div>
    <div class="ui modal large" id="modal-point-detail-match">
      <div class="header">Thông tin trận đấu</div>
      <div v-if="matchPointMember.listDetailMatch" class="content">
        <div class="ui grid">
          <div class="two column centered row">
            <h3>Trận {{matchPointMember.id}}  ({{matchPointMember.start_time}})</h3>
          </div>
          <div class="three column centered row">
            <div class="column nameteam_point">{{matchPointMember.listDetailMatch[0].name_team}}</div>
            <div
              class="column nameteam_point"
            >{{matchPointMember.listDetailMatch[0].result}} -- {{matchPointMember.listDetailMatch[1].result}}</div>
            <div class="column nameteam_point">{{matchPointMember.listDetailMatch[1].name_team}}</div>
          </div>

          <div class="three column row" >
            <div class="seven wide column">
              <div
                class="header"
              >Danh sách thành viên ghi bàn của {{matchPointMember.listDetailMatch[0].name_team}}</div>
              <table class="ui striped table">
                <thead>
                  <tr >
                    <th>stt</th>
                    <th>NickName</th>
                    <th>Ghi được</th>
                  </tr>
                </thead>
                <tbody v-if="matchPointMember.listDetailMatch[0].listPointMDM">
                  <tr v-for="(item, index) in matchPointMember.listDetailMatch[0].listPointMDM" :key="item.id">
                    <td>{{index+1}}</td>
                    <td>{{item.nickname}}</td>
                    <td>{{item.scored}}</td>
                  </tr>
                </tbody>
              </table>
            </div>
            <div class="two wide column"></div>
            <div class="seven wide column">
              <div
                class="header"
              >Danh sách thành viên ghi bàn của {{matchPointMember.listDetailMatch[1].name_team}}</div>
              <table class="ui striped table">
                <thead>
                  <tr>
                    <th>stt</th>
                    <th>NickName</th>
                    <th>Ghi được</th>
                  </tr>
                </thead>
                <tbody v-if="matchPointMember.listDetailMatch[1].listPointMDM">
                  <tr v-for="(item, index) in matchPointMember.listDetailMatch[1].listPointMDM" :key="item.id">
                    <td>{{index+1}}</td>
                    <td>{{item.nickname}}</td>
                    <td>{{item.scored}}</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import moment from "moment";
import { callApi } from "../../Api/callApi";
export default {
  name: "LayoutUsers",
  data() {
    return {
      listTournament: [],
      idTournament: "",
      tournament: {},
      listMatch: [],
      idTournamentMatch: "",
      teams: [],
      listTeamRank: [],
      listMemberInTNM: [],
      matchPointMember: {}
    };
  },
  mounted() {
    let self = this;
    self.getListTournament();
    $("#select-dropdown-team-a").dropdown({
      onChange(value, text, $choice) {
        self.idTournament = value;
      }
    });
  },

  methods: {
    async viewMatch(id) {
      let response = await callApi("GET", "/match/get-match-detail", null, {
        id: id
      });
      if (response.data.code == "0000") {
        this.matchPointMember = response.data.payload;

        $("#modal-point-detail-match").modal("show");
      }
    },

    async getListMemberInTNM(id) {
      let response = await callApi("GET", "/getall/member-tnm", null, {
        id_tnm: id
      });
      if (response.data.code == "0000") {
        this.listMemberInTNM = response.data.payload;
      }
    },
    async getListRankTeam(id_tnm) {
      let response = await callApi("GET", "team/get-rank", null, {
        id_tournament: id_tnm
      });
      if (!response) {
        return;
      }
      if (response.data.code == "0000") {
        this.listTeamRank = response.data.payload;
      }
    },

    unencodeInfo_Tnm(info_tnmEncode) {
      return unescape(info_tnmEncode);
    },

    getStatus(sta_flg) {
      if (sta_flg == 0) {
        return '<i style="height: 17px" class="ui green text play icon "></i> Chưa thi đấu';
      }
      if (sta_flg == 2) {
        return '<i style="height: 17px" class="ui yellow text video icon "></i> Đang thi đấu';
      }
      return '<i style="height: 17px" class="ui red text pause icon "></i> Đã thi đấu';
    },

    async getListTeamInTnm() {
      let response = await callApi("GET", "team/gettournament", null, {
        id_tournament: this.idTournamentMatch
      });
      if (!response.data.code) {
        return;
      }
      if (response.data.code == "0000") {
        this.teams = response.data.payload;
      }
    },

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

    getStartTime(time) {
      if (time) {
        return moment(time).format("YYYY-MM-DD HH:mm");
      }
      return;
    },

    async getListMatch() {
      let response = await callApi("GET", "/match/get-all", null, {
        id: this.idTournamentMatch
      });
      if (response.data.code == "0000") {
        this.listMatch = response.data.payload;
        this.getListTeamInTnm();
      }
    },

    async getInfoTournament(id) {
      this.idTournamentMatch = id;
      let response = await callApi("GET", "/tournament/getbyid", null, {
        id: id
      });
      if (response.data.code == "0000") {
        this.tournament = response.data.payload;
        this.getListMatch();
        this.getListRankTeam(id);
        this.getListMemberInTNM(id);
      }
    },

    signout() {
      $("#modal-signout-user").modal("show");
    },

    async getListTournament() {
      let response = await callApi("GET", "/tournament/getall");
      if (response.data.code == "0000") {
        this.listTournament = response.data.payload;
      }
    },
    async signOutHandle() {
      let response = await callApi("POST", "/logout");
      if (response.data.code == "0000") {
        this.$store.commit("increment", null);
        this.$router.push({ name: "Login" });
      }
    }
  }
};
</script>