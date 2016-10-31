(function() {

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

	var StartCtrl = function(ChessSvc) {
		var startCtrl = this;
		startCtrl.gameId = "";
		startCtrl.name = "";

		startCtrl.joinGame = function() {

		};
		startCtrl.createGame = function() {

		}
	};

	var GameCtrl = function() {
	};

	var ChessCtrl = function() {
	};

	var ChessSvc = function($rootScope) {
	};

	ChessApp.config(["$stateProvider", "$urlRouterProvider", ChessConfig]);

	ChessApp.service("ChessSvc", ["$rootScope", ChessSvc]);

	ChessApp.controller("ChessCtrl", [ChessCtrl]);
	ChessApp.controller("StartCtrl", ["ChessSvc", StartCtrl]);
	ChessApp.controller("GameCtrl", [GameCtrl]);

})();