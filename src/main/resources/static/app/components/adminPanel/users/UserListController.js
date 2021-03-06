angular.module('app')
    .controller('UserListController', function(UserService) {

        const vm = this;

        vm.users = UserService.getAll();

        vm.search = text => {
            vm.users = UserService.getAll({text});
        };

    });