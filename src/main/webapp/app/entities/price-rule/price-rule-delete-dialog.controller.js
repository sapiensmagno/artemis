(function() {
    'use strict';

    angular
        .module('artemisApp')
        .controller('PriceRuleDeleteController',PriceRuleDeleteController);

    PriceRuleDeleteController.$inject = ['$uibModalInstance', 'entity', 'PriceRule'];

    function PriceRuleDeleteController($uibModalInstance, entity, PriceRule) {
        var vm = this;

        vm.priceRule = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PriceRule.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
