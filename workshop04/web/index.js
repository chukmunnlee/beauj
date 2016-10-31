(function() {

	var url = "ws://localhost:8080/workshop04/chess-event";

	var ChessApp = angular.module("ChessApp", ["ui.router"]);

	var ChessConfig = function($stateProvider, $urlRouterProvider) {

		$stateProvider.state("start", {
			url: "/start",
			templateUrl: "views/start.html",
			controller: "StartCtrl as startCtrl"

		}).state("game", {
			url: "/game/:gid",
			templateUrl: "views/game.html",
			controller: "GameCtrl as gameCtrl"
		});

		$urlRouterProvider.otherwise("/start");
	}

	var StartCtrl = function($state, ChessSvc) {
		var startCtrl = this;
		startCtrl.gameId = "";
		startCtrl.name = "";

		startCtrl.joinGame = function() {
		};

		startCtrl.createGame = function() {
			ChessSvc.createGame({
				name: startCtrl.name,
				gameId: startCtrl.gameId
			}).then(function(gameInfo) {
				$state.go("game", {gid: gameInfo.gameId});
			});
		}
	};

	var GameCtrl = function($stateParams) {
		var gameCtrl = this;
		gameCtrl.gameId = $stateParams.gid;
	};

	var ChessCtrl = function() {
		var chessCtrl = this;
	};

	var ChessSvc = function($rootScope, $http, $httpParamSerializerJQLike, $q) {

		this.createGame = function(formData) {
			var defer = $q.defer();
			$http({
				url: "game",
				method: "POST",
				headers: {
					"Content-Type": "application/x-www-form-urlencoded",
					"Accept": "application/json"
				},
				data: $httpParamSerializerJQLike(formData)
			}).then(function(result) {
				defer.resolve(result.data);
			});
			return (defer.promise);
		};

		this.joinGame = function(gameId) {
			var conn = { };
			conn.socket = new WebSocket(url + "/" + gameId);
			conn.socket.onopen = function() {
				if (this.onopen)
					$rootScope.$apply(function() {
						conn.onopen();
					});
			}
			conn.socket.onclose = function(evt) {
				if (this.onclose)
					$rootScope.$apply(function() {
						conn.onclose(evt);
					})
			}
			conn.socket.onmessage = function(evt) {
				if (this.onmessage)
					$rootScope.$apply(function() {
						conn.onmessage(JSON.parse(evt.data));
					})
			}
			conn.socket.onerror = function() {
				if (this.onerror)
					$rootScope.$apply(function() {
						conn.onerror();
					})
			}
			conn.close = function() {
				this.close = function() {
					this.socket.close();
				}
			}
			return (conn);
		}
	};

	ChessApp.config(["$stateProvider", "$urlRouterProvider", ChessConfig]);

	ChessApp.service("ChessSvc", 
			["$rootScope", "$http", "$httpParamSerializerJQLike", "$q", ChessSvc]);

	ChessApp.controller("ChessCtrl", [ChessCtrl]);
	ChessApp.controller("StartCtrl", ["$state", "ChessSvc", StartCtrl]);
	ChessApp.controller("GameCtrl", ["$stateParams", GameCtrl]);

})();