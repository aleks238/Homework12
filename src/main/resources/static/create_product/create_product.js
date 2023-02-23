angular.module('marketFrontendApp').controller('createProductController', function ($scope, $http, $location) {
    const contextPath = 'http://localhost:8189/market/';

    $scope.createProduct = function (){
        if ($scope.new_product == null){
            alert('Форма не заполнена');
            return
        }
        $http.post(contextPath + 'api/v1/products/save', $scope.new_product)//Добавляем в тело JSON не поля по отдельности, а сразу весь объект new_product со всеми полями
            .then(function successCallback(response) {
                $scope.new_product = null;
                alert('Новый продукт создан');
                $location.path('/store');
            },function failureCallback(response){
                console.log(response);
                alert(response.data.messages)
            });
    }
});