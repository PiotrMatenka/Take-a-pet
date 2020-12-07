angular.module('app')
    .controller('UserEditController', function($routeParams, $location, $timeout, UserService, User, $scope, $rootScope) {
        const vm = this;
        $scope.submitted = false;
        const userId = $routeParams.userId;
        $scope.registration= {
            'confirmPassword': ''
        };
        if(userId)
            vm.user = UserService.get(userId);
        else
            vm.user = new User();

        const saveCallback = () => {
            $rootScope.registerMsg = 'Wysłano maila z linkiem do dokończenia rejestracji';
            $location.path(`/user-login/`);
        };
        const errorCallback = err => {
            if (err.status == 409){
                vm.msg=`Błąd zapisu: ${err.data.message}`;}
            else {
                vm.msg = 'Podano błędne dane';
            }
        };

        vm.saveUser = () => {
            $scope.submitted = true;
            UserService.save(vm.user)
                .then(saveCallback)
                .catch(errorCallback);
        };

        const updateCallback = response => vm.msg='Zapisano zmiany';

        vm.updateUser = () => {
            UserService.update(vm.user)
                .then(updateCallback)
                .catch(errorCallback);
        };

        $scope.passwordChanged = function() {
            $scope.passwordRegExp = '^('+$scope.registration.confirmPassword+')$';
        };
    });