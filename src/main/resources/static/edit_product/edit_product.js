angular.module('marketFrontendApp').controller('editProductController', function ($scope, $http, $routeParams, $location) {
    const contextPath = 'http://localhost:8189/market/';

    $scope.prepareProductForUpdate = function (){
        $http.get(contextPath + 'api/v1/products/' + $routeParams.productId)/*Когда мы нажмем кнопку изменить мы перейдем на edit_product.html страницу и
             сработает этот контроллер. В нем автоматически вызовется функция prepareProductForUpdate() которая отправит на бэкенд запрос поиск
             по id: 'api/v1/products/111'.
             1. Если бэкенд нашел такой продукт, то бэкенд в ответ отправит JSON (productDto) с данными о продукте. Данные об этом продукте (JSON) заполнятся в
             форму, чтобы мы видели какие текущие данные. Затем продукт в $scope для дальнейшего изменения.
             2. Если такого продукта нет, то с бэкенда придет исключение, обрабатываем его alert.
            */
            .then(function successCallback(response) {
                $scope.updated_product = response.data;
            }, function failureCallback(response){
                console.log(response);
                alert(response.data.messages);
                $location.path('/store');
        });
    }

    $scope.updateProduct = function (){
        $http.put(contextPath + 'api/v1/products/update', $scope.updated_product) //Для изменения используем put запрос. Отправляем на бэкенд updated_product с новыми полями.
            // Продукт updated_product получаем с формы фронтенда, он находится в $scope.
            .then(function successCallback(response){
                $scope.updated_product = null; //после ответа бэкенда продукт был изменен, зануляем updated_product
                alert('Продукт обновлен'); //пишем сообщение
                $location.path('/store'); //перенаправляем на /store
            },function failureCallback(response){ //если бэкенд прислал ошибку
                alert(response.data.messages)
            });
    }

    $scope.prepareProductForUpdate();
});
