angular.module('app')
.controller("HomeController", function (CategoryService, AdvertisementService, $cookies, UserService){
    const vm = this;
    vm.userEmail = $cookies.get("user");
    vm.user = UserService.getByEmail(vm.userEmail);

    vm.categories = CategoryService.getAll();

    vm.search = title => {
        vm.advertisements = AdvertisementService.getAll({title});
    }
})