angular.module('app')
    .controller('AuthenticationController', function($rootScope, $location, AuthenticationService, $cookies, $http) {
        var vm = this;
        vm.credentials = {};
        vm.userEmail = $cookies.get("user");

        var loginSuccess = function() {

            location.reload();
        };
        vm.login = () => {
            AuthenticationService.authenticate(vm.credentials, loginSuccess)
            $location.path('/');

        };

        var logoutSuccess = function(response) {
            location.reload();
            $location.path('/');
        };
        vm.logout = function() {
            AuthenticationService.logout(logoutSuccess);
        };

    });