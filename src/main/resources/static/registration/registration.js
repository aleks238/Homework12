angular.module('marketFrontendApp').controller('registrationController', function ($scope, $http, $routeParams, $location) {
    const contextPath = 'http://localhost:8189/market/';

    $scope.registration = function (){
        $http.post(contextPath + 'api/v1/registration', $scope.registrationData) //Для сохранения используем post запрос. Отправляем на бэкенд JSON registration.
            .then(function successCallback(response){
                $scope.registrationData = null; //после ответа бэкенда зануляем registrationData
                alert('Регистрация успешна');
                $location.path('/welcome');
            },function failureCallback(response){ //если бэкенд прислал ошибку
                alert(response.data.messages)
            });
    }
});