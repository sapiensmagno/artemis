(function() {
    'use strict';

    angular
        .module('artemisApp')
        .controller('PriceRuleController', PriceRuleController);

    PriceRuleController.$inject = ['PriceRule'];

    function PriceRuleController(PriceRule) {

        var vm = this;

        vm.priceRules = [];

        loadAll();

        function loadAll() {
            PriceRule.query(function(result) {
                vm.priceRules = result;
                vm.searchQuery = null;
            });
        }
    }
})();
