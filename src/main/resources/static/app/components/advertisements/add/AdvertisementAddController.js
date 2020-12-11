angular.module('app')
.controller('AdvertisementAddController', function ($routeParams, AdvertisementService, $location, CategoryService,
                                                    Advertisement, ImagesService, $scope, AdvertisementCheckService) {

    const vm = this;
    $scope.submitted = false;
    $scope.confirmed = false;
    const adverId = $routeParams.adverId;
    const userId = $routeParams.userId;

    if (adverId){
        vm.advertisement = AdvertisementService.get(adverId);
        vm.images = ImagesService.getImagesById(adverId);}

    else
        vm.advertisement = new Advertisement();
        vm.advertisement.userId = userId;

    vm.categories = CategoryService.getAll()
    const saveCallback = () => {
        $location.path(`/ads/edit/${vm.advertisement.id}`);
        vm.msg = "Dodano ogłoszenie";
    };

    const errorCallback = err => {
        if (err.status == 409){
            vm.msg=`Błąd zapisu: ${err.data.message}`;}
        else {
            vm.msg = 'Podano błędne dane';
        }
    };

    vm.saveAdvertisement = () =>{
        $scope.submitted = true;
        AdvertisementService.save(vm.advertisement)
            .then(saveCallback)
            .catch(errorCallback);
    };

    vm.acceptAdvertisement = advertisement =>{
        if (confirm("Czy na pewno chcesz opublikować ogłoszenie?")){
        AdvertisementCheckService.acceptByUser(advertisement.id)
            .then(function success() {
                $scope.confirmed = true;
                vm.confirmMsg = 'Ogłoszenie jest w trakcie weryfikacji, otrzymasz potwierdzenie na skrzynkę mailową';
            })
            .catch(errorCallback);
    }};

    const updateCallback = response => vm.msg='Zapisano zmiany';
    vm.updateAdvertisement = () => {
        AdvertisementService.update(vm.advertisement)
            .then(updateCallback)
            .catch(errorCallback);
    }
    const removeCallback = response => vm.msg='Usunięto zdjęcie';
    vm.removeImage = image => {
        if (confirm('Czy na pewno chcesz usunąc zdjęcie?')){
            ImagesService.removeImage(image)
                .then(location.reload(),
                    removeCallback)
                .catch(errorCallback)
        }
    }


});