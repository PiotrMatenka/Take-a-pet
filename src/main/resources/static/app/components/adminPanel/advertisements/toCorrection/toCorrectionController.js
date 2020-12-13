angular.module('app')
.controller("ToCorrectionController", function ($http, $routeParams, AdvertisementService){
    const vm = this;
    const advertisementId = $routeParams.adverId;
    vm.advertisement = AdvertisementService.get(advertisementId);
    vm.sendEmail = function (response){
        $http({
            method: 'POST',
            url: `/api/checkingAdvertisements/${vm.advertisement.id}/toCorrection`,
            data: response
        }).then(function success(){
            vm.scsMsg = "Wysłano email";
            vm.response = {};
        }, function error(){
            vm.errMsg = "Coś poszło nie tak";
        })
    }
})
