(function () {
    angular
        .module('marketFrontendApp', ['ngRoute', 'ngStorage']) /*Мы создаем JS приложение, которое называется marketFrontendApp,
         в квадратных скобках подключенные библиотеки */
        .config(config) // при запуске автоматически вызвать функцию config
        .run(run); // при запуске автоматически вызвать функцию run

    function config($routeProvider) { //настраиваем это JS приложение с помощью функции config
        $routeProvider
            .when('/', {
                templateUrl: 'welcome/welcome.html',
                controller: 'welcomeController'     //здесь связываем html файл с контроллером
            })
            .when('/store', {
                templateUrl: 'store/store.html',
                controller: 'storeController'
            })
            .when('/edit_product/:productId', {
                templateUrl: 'edit_product/edit_product.html',
                controller: 'editProductController'
            })
            .when('/create_product', {
                templateUrl: 'create_product/create_product.html',
                controller: 'createProductController'
            })
            .when('/admin', {
                templateUrl: 'admin/admin_panel.html',
                controller: 'adminPanelController'
            })
            .when('/registration', {
                templateUrl: 'registration/registration.html',
                controller: 'registrationController'
            })
            .otherwise({
                redirectTo: '/'
            });
    }

    function run($rootScope, $http, $localStorage) {//при запуске приложения автоматически выполнится эта функция
        if ($localStorage.webmarketUser) { //раньше мы положили пользователя в $localStorage браузера теперь если в $localStorage есть пользователь, то достаем его оттуда, в нем есть токен
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.webmarketUser.token;/* Устанавливаем дефолтный хедер к http запросам и добавляем в этот
            хедер токен. Токен берем у пользователя, которого достали из $localStorage. Если пользователя в $localStorage нет значит блок if statement
            не выполнится. После этого метода если пользователь нажмет на F5 его не выбросит из приложения, т.к. при повторной загрузке JS приложения автоматически сработает
             метод run() он проверяет в $localStorage наличие пользователя от предыдущей работы и достает пользователя из $localStorage. Дальше также создаем дефолтный
              хедер и добавляем в него токен. теперь можно закрыть браузер и открыть его через 20 минут, пользователь восстановится. Нужно еще проверять что
               время существования токена не прошло*/
        }
    }
}) ();

angular.module('marketFrontendApp').controller('indexController', function ($rootScope, $scope, $http, $localStorage) {

    const contextPath = 'http://localhost:8189/market/api/v1';

    $scope.tryToAuth = function (){
        $http.post(contextPath + '/auth', $scope.user) // отправить по endpoint /auth post запрос с JSON user
            .then(function successCallback(response) {//когда возвращается successCallback
                if (response.data.token) {//response.data - тело JSON из ответа, response.data.token - получить поле токен из JSON ответа. Если токен пришел то выполняем код
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token; /*К каждому http запросу на бэкенд мы должны дописывать хедер
                    Authorization, это можно сделать в методе, где пишем params. Но это не удобно. В angularJS можно настраивать дефолтные хедеры используя
                    $http.defaults.headers. Это настройка по умолчанию любого http запроса -  $http.defaults.  Ко всем http запросам по умолчанию добавлять
                    хедер Authorization со значением  'Bearer ' + response.data.token, т.е. Bearer + токен который пришел с бэкенда. дефолтный хедер будет работать
                    пока клиент не нажмет F5, т.к. после этого загрузится новое фронтенд приложение которое не знает о предыдущем. Но когда мы на сайтах жмем F5
                    нас не выкидывает. Для этого сохраняем пользователя - используем локальное хранилище браузера $localStorage.
                    */
                    $localStorage.webmarketUser = {username: $scope.user.username, token: response.data.token};/*В браузере есть hashMap $localStorage.
                     Для того чтобы использовать локальное хранилище браузера нужно подключить библиотеку ngStorage.min.js. Здесь мы сохраняем пользователя
                     в локальном хранилище браузера под ключом webmarketUser. Мы сохраняем под ключом webmarketUser username и token. */

                    /*После сохранения пользователя в $localStorage зануляем в $scope поля user.username и user.password. Т.е. очищаем поля для ввода*/
                    $scope.user.username = null;
                    $scope.user.password = null;
                }
            }, function errorCallback (response){
            });
    };

    $scope.tryToLogout = function (){
        $scope.clearUser(); //удалить пользователя
        if ($scope.user.username){ //Зануляем поля, на случай если в форме логин и пароль осталась какая-либо информация. После logout все должно быть стерто
            $scope.user.username = null;
        }
        if ($scope.user.password){
            $scope.user.password = null;
        }
        /*В этой функции мы пытаемся выйти. В приложении не по REST это значит нужно разорвать сессию, удалить cookies и бэкенд о клиенте забывает.
        В приложении по REST бэкенд о нас никогда не помнил. Как нам здесь разорвать общение с бэкенд. Для этого напишем метод clearUser. */
    };

    $scope.clearUser = function (){
        delete $localStorage.webmarketUser; //Из $localStorage удаляем клиента, чтобы он не мог быть восстановлен оттуда
        $http.defaults.headers.common.Authorization = ''; //Очищаем хедер Authorization, это сотрет токен в этом хедере
    };

    /*
     $scope - контекст для текущего контроллера и текущего html файла
     $rootScope - глобальный контекст, если мы в нго положим какую-либо функцию, переменную то к ним можно обращаться из любого контроллера и html файла.
     Метод isUserLoggedIn лучше положить в $rootScope, т.к. проверка авторизации пользователя нужна: для формы логина, для оформления заказа,
     для кнопки история заказов этого пользователя и т.д. Т.е. эта функция нужна во многих файлах, делаем ее общедоступной положив в $rootScope.
     */
    $rootScope.isUserLoggedIn = function (){ /*Метод для проверки авторизирован клиент или нет. */
        if ($localStorage.webmarketUser){ //Если клиент есть в $localStorage значит он авторизирован, возвращаем true. В этом случае предложится страница с продуктами
            return true;
        }else {
            return false; //Если клиента нет в $localStorage значит он не авторизирован, возвращаем false. В этом случае предложится форма логина.
        }
    };
});



