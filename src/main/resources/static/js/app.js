(function(){

  var app = angular.module('notesApp',['ngRoute', 'ngMaterial']);

  app.config(['$locationProvider', '$routeProvider',
      function ($locationProvider, $routeProvider) {

        $routeProvider
          .when('/', {
            templateUrl: '/partials/notes-view.html',
            controller: 'notesController'
          })
          .when('/login', {
             templateUrl: '/partials/login.html',
             controller: 'loginController',
          })
          .otherwise('/');
      }
  ]);

  app.run(['$rootScope', '$location', 'AuthService', function ($rootScope, $location, AuthService) {
      $rootScope.$on('$routeChangeStart', function (event) {
          if ($location.path() == "/login"){
             return;
          }

          if (!AuthService.isLoggedIn()) {
              console.log('DENY');
              event.preventDefault();
              $location.path('/login');
          }
      });
      
      $rootScope.logout = function(){
    	  console.log("should log out");
    	  AuthService.clearUser();
    	  $location.path('/login');
	  };
  }]);


  app.service('AuthService', function($http){
        function login (username, password){
            return $http.post("api/login", {username: username, password: password});
        }

        function isLoggedIn(){
            return retrieveUser() !== null;
        }
        
        function storeUser(user){
            sessionStorage.setItem('authedUser', JSON.stringify(user));
        }
        
        function retrieveUser(){
        	var authedUser = sessionStorage.getItem('authedUser');
        	var parsed = authedUser ? JSON.parse(authedUser) : null;
        	return parsed;
        }
        
        function storeNotes(notes){
            sessionStorage.setItem('notes', JSON.stringify(notes));
        }
        
        
        function retrieveNotes(){
        	var notes = sessionStorage.getItem('notes');
        	var parsed = notes ? JSON.parse(notes) : null;
        	return parsed;
        }
        
        function clearUser(){
        	sessionStorage.clear();
        }
        
        function isEditCreateView(){
            var isEdit = sessionStorage.getItem('editView');
            var parsed = JSON.parse(isEdit);
 			return parsed;
        }
        
        function setEditCreateView(is){
        	var isEdit = !!is;
            sessionStorage.setItem('editView', isEdit);
        }
        
        return {
            login : login,
            isLoggedIn: isLoggedIn,
            storeUser: storeUser,
			retrieveUser: retrieveUser,
			clearUser: clearUser,
			storeNotes: storeNotes,
			retrieveNotes: retrieveNotes,
			isEditCreateView: isEditCreateView,
			setEditCreateView: setEditCreateView
        }
  });

  app.controller('loginController', function($scope, AuthService, $location){

    $scope.invalidCreds = false;
    $scope.login = {
        username : null,
        password : null
    };

    $scope.login = function(){
        AuthService.login($scope.login.username, $scope.login.password).then(function(user){
            console.log(user);
            AuthService.storeUser(user);
            AuthService.storeNotes(user?.data?.notes);
            $location.path("/");
        }, function(error){
            console.log(error);
            $scope.invalidCreds = true;
        });
    };
  });


  app.controller('notesController', function($scope, $http, $location, AuthService){

    $scope.notes = AuthService.retrieveNotes();
    $scope.user = AuthService.retrieveUser();
    $scope.isEditCreateView = AuthService.isEditCreateView;
    
    $scope.newNoteView = function(){
    	AuthService.setEditCreateView(true);
        $scope.note = {
			id : null,
	        name : null,
	        text : null
	    };
    };

    $scope.deleteNote = function (i) {
      var r = confirm("Are you sure you want to delete this note?");
      if (r == true){
        $http.post("api/deleteNote", {id: $scope.note.id}).then(function(response){
        	$scope.notes = response.data;
        	AuthService.setEditCreateView(false);
		    AuthService.storeNotes(response.data);
        	$scope.note = {
				id : null,
		        name : null,
		        text : null
		    };
        }, function(error){
        	console.log("save note response error: " + error);
        }) 
      }
    };

    $scope.viewNote = function(note){
        AuthService.setEditCreateView(true);
        if(!$scope.note){
        	$scope.note = {
				id : null,
		        name : null,
		        text : null
		    };
        }
        $scope.note.id = note.id;
        $scope.note.name = note.name;
        $scope.note.text = note.text;
    }

    $scope.cancelEdit = function(){
        console.log("cancel edit clicked");
        AuthService.setEditCreateView(false);
        $scope.note = {
			id : null,
	        name : null,
	        text : null
	    };
    };

    $scope.saveNote = function(){           
        $http.post("api/saveNote", {id: $scope.note.id, name: $scope.note.name, text: $scope.note.text}).then(function(response){
        	console.log("save note response received");
        	$scope.notes = response.data;
        	AuthService.setEditCreateView(false);
		    $scope.note = {
				id : null,
		        name : null,
		        text : null
		    };
		    AuthService.storeNotes(response.data);
        }, function(error){
        	console.log("save note response error: " + error);
        }); 
    };
  });
})();