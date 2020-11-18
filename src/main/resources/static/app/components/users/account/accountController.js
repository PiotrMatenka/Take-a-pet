angular.module('app')
.controller('AccountController', function ($routeParams, UserService, AdvertisementEndService){
    var vm = this;
    vm.userId = $routeParams.id ;
    vm.user = UserService.get(vm.userId);
    vm.userAdvertisements = UserService.getUserAdvertisements(vm.userId);

    const errorCallback = err => {
        vm.msg=`Błąd zapisu: ${err.data.message}`;
    };

    vm.finishAdvertisement = advertisement => {
        AdvertisementEndService.save(advertisement.id)
            .then(response => {
                advertisement.end = response.data;
                location.reload();
            })
            .catch(errorCallback);
    };
})