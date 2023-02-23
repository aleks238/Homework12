angular.module('marketFrontendApp').controller('storeController', function ($scope, $http, $location) {
    const contextPath = 'http://localhost:8189/market/';
    let currentPageIndex = 1;

    $scope.loadProducts = function (pageIndex = 1) {
        currentPageIndex = pageIndex;
        $http({
            url: contextPath + 'api/v1/products',
            method: 'GET',
            params: {
                partName: $scope.filter ? $scope.filter.partName : null,
                minPrice: $scope.filter ? $scope.filter.minPrice : null,
                maxPrice: $scope.filter ? $scope.filter.maxPrice : null,
                p: pageIndex
            }
        }).then(function (response) {
            console.log(response)
            $scope.productsPage = response.data; /* В ответ с бэкенда придет список продуктов на 1 странице, сохраняем ее в $scope в переменной productsPage,
            чтобы достать на фронтенде. */
            $scope.paginationArray = $scope.generatePagesIndex(1, $scope.productsPage.totalPages)/*Также в JSON со страницей которая придет с бэкенда,
            должно быть общее количество страниц - сохраним общее количество страниц в array - paginationArray, создаем array в отдельном методе. */
        });
    }

    $scope.generatePagesIndex = function (startPage, endPage){
        let array = [];
        for (let i = startPage; i < endPage + 1; i++){
            array.push(i);
        }
        return array;
    };

    $scope.navToEditProductPage = function (productId){
        $location.path('/edit_product/' + productId);
    };

    $scope.loadProducts();
});