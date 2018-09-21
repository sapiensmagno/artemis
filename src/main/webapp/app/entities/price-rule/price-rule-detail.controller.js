(function() {
    'use strict';

    angular
        .module('artemisApp')
        .controller('PriceRuleDetailController', PriceRuleDetailController);

    PriceRuleDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PriceRule', 'Product'];

    function PriceRuleDetailController($scope, $rootScope, $stateParams, previousState, entity, PriceRule, Product) {
        var vm = this;

        vm.priceRule = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('artemisApp:priceRuleUpdate', function(event, result) {
            vm.priceRule = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
