angular.module('app')
.controller('AdvertisementAddController', function ($routeParams, AdvertisementService, $location, CategoryService,
                                                    Advertisement, ImagesService, $scope, AdvertisementCheckService,
                                                    $cookies, UserService) {


    const vm = this;
    $scope.submitted = false;
    $scope.confirmed = false;
    vm.user = false;
    const adverId = $routeParams.adverId;
    const userId = $routeParams.userId;




    if (adverId){
        vm.advertisement = AdvertisementService.get(adverId);
        vm.images = ImagesService.getImagesById(adverId);
        vm.userCookie = $cookies.get("user");
        vm.userByEmail = UserService.getByEmail(vm.userCookie);
    }
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

    const updateCallback = () => vm.msg='Zapisano zmiany';
    vm.updateAdvertisement = () => {
        AdvertisementService.update(vm.advertisement)
            .then(updateCallback)
            .catch(errorCallback);
    }
    const removeCallback = () => vm.msg='Usunięto zdjęcie';
    vm.removeImage = image => {
        if (confirm('Czy na pewno chcesz usunąc zdjęcie?')){
            ImagesService.removeImage(image)
                .then(location.reload(),
                    removeCallback)
                .catch(errorCallback)
        }
    }


});