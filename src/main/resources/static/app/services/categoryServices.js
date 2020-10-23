angular.module('app')
    .constant('CATEGORY_ENDPOINT', '/api/categories')
    .service('CategoryService', function($resource, CATEGORY_ENDPOINT) {
        this.getAll = () => $resource(CATEGORY_ENDPOINT).query();
    });