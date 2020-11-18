angular.module('app')
    .constant('LOGIN_ENDPOINT', '/user-login')
    .constant('LOGOUT_ENDPOINT', '/user-logout')
    .service('AuthenticationService', function($http, LOGIN_ENDPOINT, LOGOUT_ENDPOINT, $rootScope, $cookies, $location) {
        this.authenticate = function(credentials, successCallback) {
            var authHeader = {Authorization: 'Basic ' + btoa(credentials.email+':'+credentials.password)};
            var config = {headers: authHeader};
            $http
                .post(LOGIN_ENDPOINT, {}, config)
                .then(function success(value) {
                    $http.defaults.headers.post.Authorization = authHeader.Authorization;
                    $cookies.put("user", credentials.email);
                    $rootScope.errmsg = '';
                    successCallback();
                }, function error(err) {
                    if (err.status == 403 ) {
                        $rootScope.errmsg = 'ups, spróbuj ponownie';
                        $location.path('/user-login');
                    }
                    else
                        {
                            $rootScope.errmsg = 'Błędny email lub hasło';
                            $location.path('/user-login')

                        }

                });
        }
        this.logout = function(successCallback) {
            $http.post(LOGOUT_ENDPOINT, {})
                .then(function success(){
                    $cookies.remove("user");
                    successCallback();
                }, function error (err){
                    $rootScope.errmsg = 'ups, spróbuj ponownie'
                    $location.path('/user-login');
                });
        }

    });